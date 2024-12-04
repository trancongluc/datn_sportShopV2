package com.example.sportshopv2.controller;

import com.example.sportshopv2.dto.HoaDonChiTietDTO;
import com.example.sportshopv2.model.HoaDon;
import com.example.sportshopv2.repository.HoaDonRepo;
import com.example.sportshopv2.repository.SanPhamChiTietRepository;
import com.example.sportshopv2.repository.SanPhamRepository;

import com.example.sportshopv2.service.KhachhangService;
import com.example.sportshopv2.service.SanPhamChiTietService;

import com.example.sportshopv2.service.impl.HoaDonServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/doi-tra")
public class DoiTraController {

    @Autowired
    private HoaDonServiceImp hoaDonServiceImp;

    @Autowired
    private KhachhangService userService; // Dịch vụ để lấy thông tin người dùng

    @GetMapping("/view")
    public String DoiTraView() {

        return "DoiTra/DoiTra";
    }

    @GetMapping("/detail")
    public String getHoaDonDetail(@RequestParam("maHoaDon") Integer maHoaDon, Model model) {

        HoaDon hoaDon = hoaDonServiceImp.getBillDetailById(maHoaDon);
        if (hoaDon == null) {
            // Xử lý trường hợp không tìm thấy hóa đơn
            model.addAttribute("error", "Không tìm thấy hóa đơn với mã: " + maHoaDon);
            return "doi-tra/view";
        }
        model.addAttribute("hoaDon", hoaDon);
        return "DoiTra/DoiTraChiTiet";
    }
    @GetMapping("/cap-nhat")
    public String doiTra(@RequestParam("maHoaDon") Integer maHoaDon, Model model) {
        hoaDonServiceImp.updateTrangThai(maHoaDon, "Hoàn trả");
        model.addAttribute("message", "Trạng thái hóa đơn đã được cập nhật thành công.");
        return "redirect:/doi-tra/view";
    }

}