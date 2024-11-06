package com.example.sportshopv2.controller.SanPham;

import com.example.sportshopv2.model.CoGiay;
import com.example.sportshopv2.model.SanPhamChiTiet;
import com.example.sportshopv2.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/san-pham-chi-tiet")
@RequiredArgsConstructor
public class SanPhamChiTietConTroller {
    private final ChatLieuService chatLieuService;
    private final CoGiayService coGiayService;
    private final DeGiayService deGiayService;
    private final KichThuocService kichThuocService;
    private final MauSacService mauSacService;
    private final SanPhamService sanPhamService;
    private final TheLoaiService theLoaiService;
    private final ThuongHieuService thuongHieuService;
    private final SanPhamChiTietService spctService;

    @GetMapping("")
    public String sanPhamChiTiet(Model model) {
        model.addAttribute("cl", chatLieuService.getAll());
        model.addAttribute("cg", coGiayService.getAll());
        model.addAttribute("th", thuongHieuService.getAll());
        model.addAttribute("dg", deGiayService.getAll());
        model.addAttribute("tl", theLoaiService.getAll());
        model.addAttribute("th", thuongHieuService.getAll());
        model.addAttribute("kt", kichThuocService.getAllKichThuoc());
        model.addAttribute("ms", mauSacService.getAllMauSac());
        return "SanPham/them-san-pham";
    }
    @PostMapping("/them-san-pham-chi-tiet")
    @ResponseBody
    public ResponseEntity<String> themChatLieu(@RequestBody SanPhamChiTiet spct) {
        spctService.addSPCT(spct);

        return ResponseEntity.ok("Thêm thành công");
    }
}
