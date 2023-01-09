package com.gleon.bktime.controllers;

import com.gleon.bktime.models.User;
import com.gleon.bktime.repositories.UserRepository;
import com.gleon.bktime.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProfileController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;



    @GetMapping("/profile")
    public String profile(Model model){

        Integer userId = userRepository.verifyUsernameOrEmail(SecurityContextHolder.getContext().getAuthentication().getName()).getId();

        User user = userRepository.findUserById(userId);

        model.addAttribute("user", user);
        return "/profile.html";
    }

}
