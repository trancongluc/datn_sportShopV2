

package com.example.sportshopv2.controller;

import com.example.sportshopv2.model.PhieuGiamGia;
import com.example.sportshopv2.repository.PhieuGiamGiaChiTietResponsitory;
import com.example.sportshopv2.repository.PhieuGiamGiaResponsitory;
import com.example.sportshopv2.service.PhieuGiamGiaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class PhieuGiamGiaController {

    @Autowired
    private PhieuGiamGiaResponsitory vcRepo;

    @Autowired
    private PhieuGiamGiaChiTietResponsitory vcctRepo;

    @Autowired
    private PhieuGiamGiaService voucherService;

    @GetMapping("/giam-gia")
    public String GiamGia(Model model) {
        model.addAttribute("listVCCT", vcctRepo.findAll());
        model.addAttribute("listVC", vcRepo.findAll());
        return "PhieuGiamGia/giamGia";
    }

    @GetMapping("/add-giam-gia")
    public String AddGiamGia(Model model) {
        model.addAttribute("voucher", new PhieuGiamGia());
        return "PhieuGiamGia/add";
    }

    @GetMapping("/dot-giam-gia")
    public String DotGiamGia() {
        return "PhieuGiamGia/dotGiamGia";
    }

    @GetMapping("/add-dot-giam-gia")
    public String AddDotGiamGia() {
        return "PhieuGiamGia/addDotGiamGia";
    }

    @PostMapping("/save")
    public String saveVoucher(@ModelAttribute PhieuGiamGia voucher) {
        voucherService.create(voucher);
        return "redirect:/giam-gia";
    }
}