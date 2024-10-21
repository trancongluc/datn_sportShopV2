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

<<<<<<< HEAD

    @GetMapping("/d")
    public String d() {
        return "BanHangTaiQuay/BanHangTaiQuay";
    }


    @GetMapping("/e")
    public String e() {
        return "NhanVien/nhan-vien-add";
    }

    @GetMapping("/giam-gia")
    public String GiamGia() {
        return "PhieuGiamGia/giamGia";
    }

    @GetMapping("/add-giam-gia")
    public String AddGiamGia() {
        return "PhieuGiamGia/add.html";
    }

    @GetMapping("/dot-giam-gia")
    public String DotGiamGia() {
        return "PhieuGiamGia/dotGiamGia";
    }

    @GetMapping("/add-dot-giam-gia")
    public String AddDotGiamGia() {
        return "PhieuGiamGia/addDotGiamGia";
    }


    @GetMapping("/mua-sam")
    public String MuaSam() {
        return "MuaHang/view";

    }
=======


>>>>>>> tan

}
