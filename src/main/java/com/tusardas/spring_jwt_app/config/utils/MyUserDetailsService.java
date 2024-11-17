package com.tusardas.spring_jwt_app.config.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.tusardas.spring_jwt_app.domain.User;
import com.tusardas.spring_jwt_app.repository.UserRepository;

public class MyUserDetailsService implements UserDetailsService {
    Logger log = LoggerFactory.getLogger(MyUserDetailsService.class);
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("username ----------------------------------> " + username);
        User user = userRepository.getUserByEmail(username);
        log.info("user ----------------------------------> " + user);
        if(user != null) {
            return new MyUserDetails(user);
        }
        else {
            throw new UsernameNotFoundException("Could not find user");
        }
    }
    
}
