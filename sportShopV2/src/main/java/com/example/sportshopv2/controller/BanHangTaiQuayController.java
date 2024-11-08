package com.example.sportshopv2.controller;

import com.example.sportshopv2.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/ban-hang-tai-quay")
@RequiredArgsConstructor
public class BanHangTaiQuayController {
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
    public String banHangTaiQuay() {
        return "BanHangTaiQuay/BanHangTaiQuay";
    }
}
