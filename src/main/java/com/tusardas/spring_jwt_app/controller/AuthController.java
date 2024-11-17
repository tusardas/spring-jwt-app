package com.tusardas.spring_jwt_app.controller;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.tusardas.spring_jwt_app.config.pojo.LoginRequest;
import com.tusardas.spring_jwt_app.config.utils.TokenService;


@RestController
public class AuthController {
    Logger log = LoggerFactory.getLogger(AuthController.class);
    
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    public AuthController(TokenService tokenService, AuthenticationManager authenticationManager) {
        this.tokenService = tokenService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/getAccessToken")
    public String postMethodName(@RequestBody LoginRequest userLogin) {
        log.info("userLogin -----------------------> " + userLogin.username());
        log.info("password -----------------------> " + userLogin.password());
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userLogin.username(), userLogin.password())
        );
        String access_token = tokenService.generateToken(authentication, 1);
        String refresh_token = tokenService.generateToken(authentication, 2);
        JSONObject json = new JSONObject();
        json.put("access_token", access_token);
        json.put("refresh_token", refresh_token);
        return json.toString();
    }
}
