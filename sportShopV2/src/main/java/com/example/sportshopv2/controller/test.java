package com.example.sportshopv2.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
<<<<<<< HEAD
=======

>>>>>>> NguyenTuanThuat
    @GetMapping("/c")
    public String c() {
        return "KhachHang/tao-khach-hang";
    }
<<<<<<< HEAD

=======
>>>>>>> NguyenTuanThuat

}
