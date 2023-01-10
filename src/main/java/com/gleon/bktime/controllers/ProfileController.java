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
    public String requestProfilePage(Model model){
        Integer userId = userRepository.verifyUsernameOrEmail(SecurityContextHolder.getContext().getAuthentication().getName()).getId();
        User user = userRepository.findUserById(userId);
        model.addAttribute("user", user);
        return "/profile.html";
    }


    @PostMapping("/profile")
    public String modifyUser(
            //@RequestParam(required = false) String userImg,
            @RequestParam(required = false) String userName,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String surname,
            //@RequestParam(required = false) String phone,
            @RequestParam(required = false) String email) {

        Integer userId = userRepository.verifyUsernameOrEmail(SecurityContextHolder.getContext().getAuthentication().getName()).getId();
        User user = userRepository.findUserById(userId);

        if (userRepository.findUserName(userName) != null && userRepository.findUserById(userId).getUserName().equals(userName)){
            user.setUserName(userRepository.findUserById(userId).getUserName());
        } else if (userRepository.findUserName(userName) == null){
            user.setUserName(userName);
        } else if (userName == null || userName.equals("")) {
            return "redirect:/profile?usernameEmpty";
        }

        if (name == null || name.equals("")) {
            return "redirect:/profile?nameEmpty";
        }
        if (surname == null || surname.equals("")) {
            return "redirect:/profile?surnameEmpty";
        }

        if (userRepository.findEmail(email) != null && userRepository.findUserById(userId).getEmail().equals(email)){
            user.setEmail(userRepository.findUserById(userId).getEmail());
        } else if (userRepository.findEmail(email) == null){
            user.setEmail(email);
        } else if (email == null || email.equals("")) {
            return "redirect:/profile?emailEmpty";
        }

        //if (userImg != null) user.setUserImg(userImg);

        /*if (userName == null || userName.equals("")) {
            return "redirect:/profile?usernameEmpty";

        } else if (userRepository.findUserName(userName) != null) {
            user.setUserName(userName);

        } else {
            return "redirect:/profile?usernameInUse";
        }

        if (name == null || name.equals("")) {
            return "redirect:/profile?nameEmpty";
        } else {
            user.setName(name);
        }

        if (surname == null || surname.equals("")) {
            return "redirect:/profile?surnameEmpty";
        } else {
            user.setSurname(surname);
        }


        //user.setPhone(phone);


        if (email == null || email.equals("")) {
            return "redirect:/profile?emailEmpty";

        } else if (userRepository.findEmail(email) != null) {
            return "redirect:/profile?emailInUse";

        } else if (userRepository.findEmail(email).equals(user.getEmail())) {
            user.setEmail(email);

        } else {
            user.setEmail(email);

        }*/
        userService.saveUser(user);
        return "redirect:/profile?success";
    }

    @GetMapping("/password")
    public String requestPasswordPage(){
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
