package com.example.sportshopv2.controller.SanPham;

import com.example.sportshopv2.dto.SanPhamChiTietDTO;
import com.example.sportshopv2.model.SanPhamChiTiet;
import com.example.sportshopv2.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
    @ModelAttribute
    public void addCommonAttributes(Model model) {
        model.addAttribute("cl", chatLieuService.getAll());
        model.addAttribute("cg", coGiayService.getAll());
        model.addAttribute("th", thuongHieuService.getAll());
        model.addAttribute("dg", deGiayService.getAll());
        model.addAttribute("tl", theLoaiService.getAll());
        model.addAttribute("kt", kichThuocService.getAllKichThuoc());
        model.addAttribute("ms", mauSacService.getAllMauSac());
    }
    @GetMapping("")
    public String sanPhamChiTiet(Model model) {
        return "SanPham/them-san-pham";
    }

    @GetMapping("/{idSP}")
    public String getSPCTByIdSP(
            @PathVariable("idSP") Integer idSP,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
            , Model model) {
        Page<SanPhamChiTietDTO> listSPCT = spctService.getSPCTByIdSP(idSP, PageRequest.of(page, size));
        model.addAttribute("spct", listSPCT.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", listSPCT.getTotalPages());
        return "SanPham/san-pham-chi-tiet";
    }

    @PostMapping("/them-san-pham-chi-tiet")
    @ResponseBody
    public ResponseEntity<SanPhamChiTiet> themChatLieu(@RequestBody SanPhamChiTiet spct) {
        return ResponseEntity.ok(spctService.addSPCT(spct));
    }
}
