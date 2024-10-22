package com.example.sportshopv2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SanPhamController {
    @GetMapping("/san-pham")
    public String SanPham() {
        return "MuaHang/san-pham";
    }

}
