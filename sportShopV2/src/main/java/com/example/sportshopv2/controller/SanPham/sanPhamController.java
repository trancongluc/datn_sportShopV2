package com.example.sportshopv2.controller.SanPham;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("san-pham")
public class sanPhamController {
    @GetMapping("")
    public String sanPham() {
        return "SanPham/san-pham";
    }
    @GetMapping("/them-san-pham")
    public String theLoai() {
        return "SanPham/them-san-pham";
    }
}
