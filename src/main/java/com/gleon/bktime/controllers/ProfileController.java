package com.gleon.bktime.controllers;

import com.gleon.bktime.models.User;
import com.gleon.bktime.repositories.UserRepository;
import com.gleon.bktime.security.SecurityConfig;
import com.gleon.bktime.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
public class ProfileController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private SecurityConfig securityConfig;

    @Autowired
    private HttpSession session;


    private User onchangeUser;


    @GetMapping("/profile")
    public String requestProfilePage(Model model){
        Integer userId = userRepository.verifyUsernameOrEmail(SecurityContextHolder.getContext().getAuthentication().getName()).getId();
        User user = userRepository.findUserById(userId);
        model.addAttribute("user", user);
        return "/profile.html";
    }


    @PostMapping("/profile")
    public String modifyUser(
            //@ModelAttribute("") String userImg,
            @ModelAttribute("userName") String userName,
            @ModelAttribute("name") String name,
            @ModelAttribute("surname") String surname,
            //@ModelAttribute("") String phone,
            @ModelAttribute("email") String email) {

        Integer userId = userRepository.verifyUsernameOrEmail(SecurityContextHolder.getContext().getAuthentication().getName()).getId();
        onchangeUser = userRepository.findUserById(userId);

        if (userRepository.findUserName(userName) != null && userRepository.findUserById(userId).getUserName().equals(userName)){
            onchangeUser.setUserName(userRepository.findUserById(userId).getUserName());
        } else if (userRepository.findUserName(userName) == null){
            onchangeUser.setUserName(userName);
        } else if (userName == null || userName.equals("")) {
            return "redirect:/profile?usernameEmpty";
        }

        if (name == null || name.equals("")) {
            return "redirect:/profile?nameEmpty";
        } else {
            onchangeUser.setName(name);
        }

        if (surname == null || surname.equals("")) {
            return "redirect:/profile?surnameEmpty";
        } else {
        onchangeUser.setSurname(surname);
        }
/*
        if (userRepository.findEmail(email) != null && userRepository.findUserById(userId).getEmail().equals(email)){
            onchangeUser.setEmail(userRepository.findUserById(userId).getEmail());
        } else if (userRepository.findEmail(email) == null){
            onchangeUser.setEmail(email);
        } else if (email == null || email.equals("")) {
            return "redirect:/profile?emailEmpty";
        }
*/


        if (email == null || email.equals("")) {
            return "redirect:/profile?emailEmpty";
        } else if (userRepository.findEmail(email) != null && userRepository.findUserById(userId).getEmail().equals(email)) {
            return "redirect:/profile?emailExist";
        }else {
            onchangeUser.setEmail(email);
        }


        session.setAttribute("onchangeUser", onchangeUser);

        return "/verifyPassword.html";
    }



    @PostMapping("/passwordVerification")
    public String verifyPage(@ModelAttribute("verifyPassword") String verifyPassword){

        if (verifyPassword == null || verifyPassword.equals("")){
            return "redirect:/verifyPassword?error";
        } else if (securityConfig.passwordEncoder().matches(verifyPassword, userRepository.findUserById(onchangeUser.getId()).getPassword())){
            userService.saveUserWithEncode(onchangeUser, false);
        }else{
            return "redirect:/verifyPassword?mismatch";
        }

        return "redirect:/profile?success";
    }

    @GetMapping("/password")
    public String requestPasswordPage(){
        return "/password.html";
    }

    @PostMapping("/password")
    public String changePasswordRequest(
            @ModelAttribute("oldPassword") String oldPassword,
            @ModelAttribute("newPassword") String newPassword){

        Integer userId = userRepository.verifyUsernameOrEmail(SecurityContextHolder.getContext().getAuthentication().getName()).getId();
        User toSaveUser = userRepository.findUserById(userId);


        if (securityConfig.passwordEncoder().matches(oldPassword, userRepository.findUserById(userId).getPassword()) && newPassword != null){

            toSaveUser.setPassword(newPassword);

            userService.saveUserWithEncode(toSaveUser, true);

            return "redirect:/password?success";
        } else {
            return "redirect:/password?error";
        }

    }



}
