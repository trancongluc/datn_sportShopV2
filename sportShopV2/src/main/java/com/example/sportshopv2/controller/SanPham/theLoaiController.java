package com.example.sportshopv2.controller.SanPham;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class theLoaiController {
    @GetMapping("/the-loai")
    public String theLoai() {
        return "SanPham/the-loai";
    }
}
