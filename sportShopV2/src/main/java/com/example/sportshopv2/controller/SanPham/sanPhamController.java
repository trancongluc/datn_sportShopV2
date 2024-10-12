package com.example.sportshopv2.controller.SanPham;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class sanPhamController {
    @GetMapping("/san-pham")
    public String theLoai() {
        return "SanPham/san-pham";
    }
}
