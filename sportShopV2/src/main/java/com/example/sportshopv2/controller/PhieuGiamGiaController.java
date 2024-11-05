package com.example.sportshopv2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PhieuGiamGiaController {
    @GetMapping("/giam-gia")
    public String GiamGia() {
        return "PhieuGiamGia/giamGia";
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
