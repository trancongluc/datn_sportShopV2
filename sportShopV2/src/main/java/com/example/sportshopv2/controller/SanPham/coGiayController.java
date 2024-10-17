package com.example.sportshopv2.controller.SanPham;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class coGiayController {
    @GetMapping("/co-giay")
    public String theLoai() {
        return "SanPham/co-giay";
    }
}
