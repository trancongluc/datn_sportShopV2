package com.example.sportshopv2.controller.SanPham;

import com.example.sportshopv2.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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

    @GetMapping("")
    public String sanPhamChiTiet(Model model) {
        model.addAttribute("cl", chatLieuService.getAll());
        model.addAttribute("cg", coGiayService.getAll());
        model.addAttribute("th", thuongHieuService.getAll());
        model.addAttribute("dg", deGiayService.getAll());
        model.addAttribute("tl", theLoaiService.getAll());
        model.addAttribute("th", thuongHieuService.getAll());
        return "SanPham/them-san-pham";
    }
}
