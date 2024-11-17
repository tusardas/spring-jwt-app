package com.tusardas.spring_jwt_app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tusardas.spring_jwt_app.domain.User;
import com.tusardas.spring_jwt_app.repository.UserRepository;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public List<User> findAllUsers() {
        List<User> userList = userRepository.findAll();
        return userList;
    }
}
