package com.application.virgo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/site")
public class RedirectController {

    @GetMapping("/vai")
    public String get() {
        return "Home";
    }

    @PostMapping("/vai")
    public String get2() {
        return "Home";
    }

    @GetMapping("/fail")
    public String post() {
        return "Fail";
    }

    @PostMapping("/fail")
    public String get3() {
        return "Fail";
    }
}
