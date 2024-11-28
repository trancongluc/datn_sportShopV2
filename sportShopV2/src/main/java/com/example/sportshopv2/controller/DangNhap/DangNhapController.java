package com.example.sportshopv2.controller.DangNhap;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/login")
public class DangNhapController {
    @GetMapping("/home")
    public String login(){
        return "DangNhap/DangNhap";
    }
//    @GetMapping("/logout")
//    public String logout(){
//        return "DangNhap/DangNhap";
//    }
    @GetMapping("/access")
    public String access(){
        return "DangNhap/TruyCap";
    }
}
