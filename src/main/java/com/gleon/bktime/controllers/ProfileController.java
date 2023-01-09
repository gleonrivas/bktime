package com.gleon.bktime.controllers;

import com.gleon.bktime.models.User;
import com.gleon.bktime.repositories.UserRepository;
import com.gleon.bktime.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
public class ProfileController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @GetMapping("/profile")
    public String profile(Model model, UserDetails userDetails){
        User user = new User();
        user.setName(userDetails.getUsername());

        model.addAttribute("user", user);
        return "/profile.html";
    }

}
