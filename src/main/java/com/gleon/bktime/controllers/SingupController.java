package com.gleon.bktime.controllers;


import com.gleon.bktime.models.User;
import com.gleon.bktime.repositories.UserRepository;
import com.gleon.bktime.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class SingupController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    //Request of the view.
    @GetMapping("/singup")
    public String singupRequest(){
        return "/singup.html";
    }


    @ModelAttribute("user")
    public User returnNewUser(){
        return new User();
    }

    @PostMapping("/singup")
    public String singupSaveDataRequest(@ModelAttribute("user") User user){

        boolean existEmail = userRepository.findEmail(user.getEmail()) != null;
        boolean existUsername = userRepository.findUserName(user.getUserName()) != null;

        if(existUsername){
            return "redirect:/singup?alreadyExistUsername";
        } else if(existEmail){
            return "redirect:/singup?alreadyExistEmail";
        }else {
            userService.saveUserWithEncode(user, true);
            return "redirect:/login?success";
        }

    }

}
