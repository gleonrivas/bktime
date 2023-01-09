package com.gleon.bktime.controllers;

import com.gleon.bktime.models.User;
import com.gleon.bktime.models.UserType;
import com.gleon.bktime.repositories.UserRepository;
import com.gleon.bktime.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
public class LoginController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String loginRequest(){
        return "/login.html";
    }

    @GetMapping("/logout")
    public String logoutRequest(){
        return "/login?logout";
    }




}
