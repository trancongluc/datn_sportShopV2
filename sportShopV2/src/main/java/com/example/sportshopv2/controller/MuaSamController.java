package com.example.sportshopv2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MuaSamController {
    @GetMapping("/mua-sam")
    public String MuaSam(){
        return "MuaHang/view";
    }
    @GetMapping("/thong-tin-san-pham")
    public String thongTinSP(){
        return "MuaHang/sanPhamKH";
    }
    @GetMapping("/thong-tin-chi-tiet-san-pham")
    public String thongTinChiTietSP(){
        return "MuaHang/chiTietSanPhamKH";
    }
}

