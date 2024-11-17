package com.tusardas.spring_jwt_app.config.utils;

import org.springframework.security.crypto.password.PasswordEncoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class MyPasswordEncoder implements PasswordEncoder{
    Logger log = LoggerFactory.getLogger(MyPasswordEncoder.class);

    @Override
    public String encode(CharSequence rawPassword) {
        String input = rawPassword.toString();
        String output = input;
        return output;
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        log.info("rawPassword --------------------->" + rawPassword);
        log.info("encodedPassword --------------------->" + encodedPassword);
        return encode(rawPassword).equals(encodedPassword);
    }
    
}
