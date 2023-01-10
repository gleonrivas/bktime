package com.gleon.bktime.controllers;

import com.gleon.bktime.models.User;
import com.gleon.bktime.repositories.UserRepository;
import com.gleon.bktime.security.SecurityConfig;
import com.gleon.bktime.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ProfileController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private SecurityConfig securityConfig;



    @GetMapping("/profile")
    public String requestProfile(Model model){
        Integer userId = userRepository.verifyUsernameOrEmail(SecurityContextHolder.getContext().getAuthentication().getName()).getId();
        User user = userRepository.findUserById(userId);
        model.addAttribute("user", user);
        return "/profile.html";
    }


    @GetMapping("/password")
    public String requestPassword(){
        return "/password.html";
    }

    @PostMapping("/password")
    public String changePasswordRequest(
            @RequestParam(required = false) String oldPassword,
            @RequestParam(required = false) String newPassword){

        Integer userId = userRepository.verifyUsernameOrEmail(SecurityContextHolder.getContext().getAuthentication().getName()).getId();
        User toSaveUser = userRepository.findUserById(userId);


        if (securityConfig.passwordEncoder().matches(oldPassword, userRepository.findUserById(userId).getPassword()) && newPassword != null){

            toSaveUser.setPassword(newPassword);

            userService.saveUser(toSaveUser);

            return "redirect:/password?success";
        } else {
            return "redirect:/password?error";
        }

    }

}
