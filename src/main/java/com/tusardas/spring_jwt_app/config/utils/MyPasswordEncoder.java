package com.tusardas.spring_jwt_app.config.utils;

import org.springframework.security.crypto.password.PasswordEncoder;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MyPasswordEncoder implements PasswordEncoder{
    Logger log = LoggerFactory.getLogger(MyPasswordEncoder.class);
    private final String SECURITY_SALT = "XXXXX";
    private final String SECURITY_ENCODING = "UTF-8";
    private final String SECURITY_ALGORITHM = "SHA1";

    public String padLeft1(int n, char c, String s) {
        return String.format("%" + n + "s", s).replace(' ', c);  
    }

    @Override
    public String encode(CharSequence rawPassword) {
        String input = rawPassword.toString();
        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance(SECURITY_ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        String salt_mixed_input = SECURITY_SALT.concat(input);
        try {
            digest.update(salt_mixed_input.getBytes(SECURITY_ENCODING));
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        String unpadded = new BigInteger(1, digest.digest()).toString(16);
        String padded = StringUtils.leftPad(unpadded, 32, "0");
        return padded;
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        log.info("rawPassword --------------------->" + rawPassword);
        log.info("encodedPassword --------------------->" + encodedPassword);
        return encode(rawPassword).equals(encodedPassword);
    }
    
}
