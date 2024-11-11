package com.example.sportshopv2.controller;

import com.example.sportshopv2.repository.PhieuGiamGiaChiTietResponsitory;
import com.example.sportshopv2.repository.PhieuGiamGiaResponsitory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PhieuGiamGiaController {
    @Autowired
    PhieuGiamGiaResponsitory vcRepo;
    @Autowired
    PhieuGiamGiaChiTietResponsitory vcctRepo;


    @GetMapping("/giam-gia")
    public String GiamGia(Model model) {
            model.addAttribute("listVCCT",vcctRepo.findAll());
            model.addAttribute("listVC",vcRepo.findAll());


        return "PhieuGiamGia/giamGia.html";
    }

    @GetMapping("/add-giam-gia")
    public String AddGiamGia() {
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

}
