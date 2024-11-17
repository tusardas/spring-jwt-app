package com.tusardas.spring_jwt_app.config.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.stream.Collectors;

@Service
public class TokenService {
    private Logger log = LoggerFactory.getLogger(TokenService.class);

    private final JwtEncoder encoder;

    public TokenService(JwtEncoder encoder) {
        this.encoder = encoder;
    }

    public String generateToken(Authentication authentication, Integer tokenType) {
        Instant now = Instant.now();
        Instant expiry;
        String scope;
        if(tokenType == 1) {
            expiry = now.plus(5, ChronoUnit.MINUTES);
            scope = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining("RESOUCRE_ACCESS"));
        }
        else {
            expiry = now.plus(365, ChronoUnit.DAYS);
            scope = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining("REFRESH_ACCESS"));
        }
        log.info("TokenType = " + tokenType + " | Expiry -------------------------> " + expiry);
        log.info("TokenType = " + tokenType + " | Scope -------------------------> " + scope);

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .expiresAt(expiry)
                .subject(authentication.getName())
                .claim("scope", scope)
                .claim("tokenType", tokenType)
                .build();
        return this.encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

}