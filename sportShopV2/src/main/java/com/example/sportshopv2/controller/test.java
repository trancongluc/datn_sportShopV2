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
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
=======
    @GetMapping("/d")
    public String d() {
        return "BanHangTaiQuay/BanHangTaiQuay";
    }
>>>>>>> long
=======
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
>>>>>>> minh

=======
>>>>>>> NguyenTuanThuat

=======
    @GetMapping("/giam-gia")
        public String GiamGia(){
            return "PhieuGiamGia/giamGia";
    }
    @GetMapping("/add-giam-gia")
    public String AddGiamGia(){
        return "PhieuGiamGia/add.html";
    }
>>>>>>> tan
}
