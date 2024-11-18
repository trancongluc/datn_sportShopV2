package com.example.sportshopv2.controller.DatHang;

import com.example.sportshopv2.dto.SanPhamChiTietDTO;
import com.example.sportshopv2.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/mua-sam-SportShopV2")
public class DatHangController {
    private ChatLieuService chatLieuService;
    private CoGiayService coGiayService;
    private DeGiayService deGiayService;
    private KichThuocService kichThuocService;
    private MauSacService mauSacService;
    private SanPhamService sanPhamService;
    private TheLoaiService theLoaiService;
    private ThuongHieuService thuongHieuService;
    @Autowired
    private  SanPhamChiTietService sanPhamChiTietService;

    @RequestMapping("/trang-chu")
    public String trangChu(Model model) {
        List<SanPhamChiTietDTO> AllProductDetail = sanPhamChiTietService.getAllSPCT();
        model.addAttribute("spct", AllProductDetail);
        return "MuaHang/view";
    }
}
