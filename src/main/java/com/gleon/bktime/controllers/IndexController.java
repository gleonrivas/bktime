package com.gleon.bktime.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

    @GetMapping("/")
    public String index(){
        return "redirect:/profile";
    }

    @RequestMapping("/404")
    public String E404(){
        return "redirect:/404";
    }

}
