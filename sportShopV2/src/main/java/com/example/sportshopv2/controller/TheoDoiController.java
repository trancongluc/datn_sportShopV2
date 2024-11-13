package com.example.sportshopv2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TheoDoiController {
    @GetMapping("/theo-doi")
    public String f() {
        return "TheoDoi/TheoDoi";
    }
}
