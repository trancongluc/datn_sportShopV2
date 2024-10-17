package com.example.sportshopv2.controller.SanPham;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
@Controller
public class thuongHieuController {
    @GetMapping("/thuong-hieu")
    public String theLoai() {
        return "SanPham/thuong-hieu";
    }
}
