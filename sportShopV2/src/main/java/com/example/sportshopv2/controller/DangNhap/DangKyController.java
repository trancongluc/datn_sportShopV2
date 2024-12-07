package com.example.sportshopv2.controller.DangNhap;

import com.example.sportshopv2.model.NguoiDung;
import com.example.sportshopv2.model.TaiKhoan;
import com.example.sportshopv2.repository.NguoiDungRepo;
import com.example.sportshopv2.repository.TaiKhoanRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/login")
public class DangKyController {
    @Autowired
    private NguoiDungRepo nguoiDungRepo;
    @Autowired
    private TaiKhoanRepo taiKhoanRepo;

    @GetMapping("/view-new-account")
    public String viewNewAccount() {
        return "DangNhap/DangKy";
    }

    @PostMapping("/newAccount")
    public String newAccount(@ModelAttribute("NguoiDung") NguoiDung nd, @ModelAttribute("TaiKhoan") TaiKhoan tk) {
        nd.setCreate_by("admin");
        nd.setCreate_at(LocalDateTime.now());
        nd.setUpdate_at(LocalDateTime.now());
        nd.setUpdate_by("admin");
        nd.setCode("User");
        // Lưu NguoiDung trước để lấy ID
        nguoiDungRepo.save(nd);

        // Mã hóa mật khẩu và thiết lập thông tin cho TaiKhoan
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        tk.setPassword("{bcrypt}" + encoder.encode(tk.getPassword()));
        tk.setRole("Employee");

        tk.setCreate_at(LocalDateTime.now());
        tk.setCreate_by("admin");
        tk.setStatus("Active");
        tk.setDeleted(false);
        tk.setUpdate_at(LocalDateTime.now());
        tk.setUpdate_by("admin");
        tk.setNguoiDung(nd); // Thiết lập khóa ngoại
        tk.setUsername(nd.getEmail());
        taiKhoanRepo.save(tk);
        return "redirect:/login/home";
    }


}
