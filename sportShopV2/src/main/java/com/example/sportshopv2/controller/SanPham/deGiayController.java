package com.example.sportshopv2.controller.SanPham;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class deGiayController {
    @GetMapping("/de-giay")
    public String theLoai() {
        return "SanPham/de-giay";
    }
}
