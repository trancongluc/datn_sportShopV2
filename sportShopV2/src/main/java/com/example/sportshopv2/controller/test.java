package com.example.sportshopv2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class test {

    @GetMapping("/a")
    public String a() {
        return "Menu";
    }

    @GetMapping("/b")
    public String b() {
        return "KhachHang/khachhang";
    }

    @GetMapping("/c")
    public String c() {
        return "KhachHang/tao-khach-hang";
    }

    //<<<<<<< Updated upstream
//=======
    @GetMapping("/d")
    public String d() {
        return "NhanVien/QL-nhan-vien";
    }

    @GetMapping("/e")
    public String e() {
        return "NhanVien/nhan-vien-add";
    }

    //>>>>>>> Stashed changes
    @GetMapping("/f")
    public String f() {
        return "GioHang/giohang";
    }
    @GetMapping("/g")
    public String g() {
        return "GioHang/giohang";
    }
}
