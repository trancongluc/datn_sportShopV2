package com.example.sportshopv2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MuaSamController {
    @GetMapping("/mua-sam")
    public String MuaSam(){
        return "MuaHang/view";
    }

    @GetMapping("/mua-ngay")
    public String MuaNgay(){
        return "MuaHang/mua-ngay";
    }
}
