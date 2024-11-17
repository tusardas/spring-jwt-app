package com.tusardas.spring_jwt_app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.tusardas.spring_jwt_app.domain.User;
import com.tusardas.spring_jwt_app.service.UserService;

@Controller
public class DashboardController {

    @Autowired
    private UserService userService;
    
    @GetMapping("/")
    public String getMethodName(Model model) {
        List<User> users = userService.findAllUsers();
        model.addAttribute("users", users);
        return "dashboard";
    }

    @GetMapping("/loginPage")
    public String login(Model model) {
        return "loginPage";
    }
}
