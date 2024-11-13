package com.example.sportshopv2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("ban-hang")
public class MuaSamController {
    @GetMapping("/mua-sam")
    public String MuaSam(){
        return "MuaHang/view";
    }

    @GetMapping("/mua-ngay")
    public String MuaNgay(){
        return "MuaHang/mua-ngay";
    }
    @GetMapping("/san-pham")
    public String SanPham() {
        return "MuaHang/san-pham";
    }
}
