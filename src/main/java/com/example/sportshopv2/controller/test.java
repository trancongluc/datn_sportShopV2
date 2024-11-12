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

    @GetMapping("/d")
    public String d() {
        return "BanHangTaiQuay/BanHangTaiQuay";
    }


    @GetMapping("/e")
    public String e() {
        return "NhanVien/nhan-vien-add";
    }






    @GetMapping("/ggkhachhang")
    public String ggkhachhang() {
        return "PhieuGiamGiaKhachHang/PhieuGiamGiaKhachHang";
    }
    @GetMapping("/thongtin")
    public String thongtin() {
        return "TaiKhoanKhachHang/TaiKhoanKhachHang";
    }

}
