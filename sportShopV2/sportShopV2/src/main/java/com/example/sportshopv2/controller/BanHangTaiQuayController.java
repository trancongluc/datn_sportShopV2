package com.example.sportshopv2.controller;

import com.example.sportshopv2.dto.SanPhamChiTietDTO;
import com.example.sportshopv2.model.User;
import com.example.sportshopv2.repository.KhachHangRepository;
import com.example.sportshopv2.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

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
    private final KhachHangRepository khRepo;

    @GetMapping("")
    public String banHangTaiQuay(Model model) {
        List<SanPhamChiTietDTO> listSPCTDto = spctService.getAllSPCT();
        List<User> listKH = khRepo.findAllKhachHang();
        model.addAttribute("spctDto", listSPCTDto);
        model.addAttribute("kh", listKH);
        return "BanHangTaiQuay/BanHangTaiQuay";
    }

    @GetMapping("/spct")
    public String getAllSPCT(Model model) {
        List<SanPhamChiTietDTO> listSPCTDto = spctService.getAllSPCT();
        model.addAttribute("spctDto", listSPCTDto);
        return "BanHangTaiQuay/BanHangTaiQuay";
    }

    @GetMapping("/spct/{id}")
    @ResponseBody
    public SanPhamChiTietDTO getSPCTById(@PathVariable("id") Integer id) {

        return spctService.getByID(id);
    }
}
