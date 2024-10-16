package com.example.sportshopv2.controller.SanPham;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class chatLieuController {
    @GetMapping("/chat-lieu")
    public String theLoai() {
        return "SanPham/chat-lieu";
    }
}
