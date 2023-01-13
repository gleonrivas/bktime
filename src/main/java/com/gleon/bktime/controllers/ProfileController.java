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

    private User toChangeUser;

    public void saveUserIdOnSesion(){
        try {
            session.setAttribute("userId", userRepository.verifyUsernameOrEmail(SecurityContextHolder.getContext().getAuthentication().getName()).getId());
        }catch (Exception ignored){}
    }

    @GetMapping("/profile")
    public String requestProfilePage(Model model, HttpSession session){
        saveUserIdOnSesion();
        User user = userRepository.findUserById((Integer) session.getAttribute("userId"));
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

        User user = userRepository.findUserById((Integer) session.getAttribute("userId"));
        toChangeUser = userRepository.findUserById(user.getId());

        if (userRepository.findUserName(userName) != null && userRepository.findUserById(user.getId()).getUserName().equals(userName)){
            toChangeUser.setUserName(userRepository.findUserById(user.getId()).getUserName());
        } else if (userRepository.findUserName(userName) == null){
            toChangeUser.setUserName(userName);
        } else if (userName == null || userName.equals("")) {
            return "redirect:/profile?usernameEmpty";
        }

        if (name == null || name.equals("")) {
            return "redirect:/profile?nameEmpty";
        } else {
            toChangeUser.setName(name);
        }

        if (surname == null || surname.equals("")) {
            return "redirect:/profile?surnameEmpty";
        } else {
            toChangeUser.setSurname(surname);
        }


        if (userRepository.findEmail(email) != null && userRepository.findUserById(user.getId()).getEmail().equals(email)){
            toChangeUser.setEmail(userRepository.findUserById(user.getId()).getEmail());
        } else if (userRepository.findEmail(email) == null){
            toChangeUser.setEmail(email);
        } else if (email == null || email.equals("")) {
            return "redirect:/profile?emailEmpty";
        }



        session.setAttribute("onchangeUser", toChangeUser);

        return "/verifyPassword.html";
    }



    @PostMapping("/passwordVerification")
    public String verifyPage(@ModelAttribute("verifyPassword") String verifyPassword){

        if (verifyPassword == null || verifyPassword.equals("")){
            return "redirect:/verifyPassword?error";
        } else if (securityConfig.passwordEncoder().matches(verifyPassword, userRepository.findUserById((Integer) session.getAttribute("userId")).getPassword())){
            userService.saveUserWithEncode(toChangeUser, false);
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

        User user = userRepository.findUserById((Integer) session.getAttribute("userId"));
        User toSaveUser = userRepository.findUserById(user.getId());


        if (securityConfig.passwordEncoder().matches(oldPassword, userRepository.findUserById(user.getId()).getPassword()) && newPassword != null){

            toSaveUser.setPassword(newPassword);

            userService.saveUserWithEncode(toSaveUser, true);

            return "redirect:/password?success";
        } else {
            return "redirect:/password?error";
        }

    }



}
