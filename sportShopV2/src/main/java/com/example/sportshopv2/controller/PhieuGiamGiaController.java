

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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class PhieuGiamGiaController {

    @Autowired
    private PhieuGiamGiaResponsitory vcRepo;

    @Autowired
    private PhieuGiamGiaChiTietResponsitory vcctRepo;

    @Autowired
    private PhieuGiamGiaService voucherService;

    @Autowired
    private PhieuGiamGiaService phieuGiamGiaService;
    @GetMapping("/giam-gia")
    public String GiamGia(Model model) {
        model.addAttribute("listVCCT", vcctRepo.findAll());
        model.addAttribute("listVC", vcRepo.findAll());
        return "PhieuGiamGia/giamGia";
    }

    @GetMapping("/add-giam-gia")
    public String showAddGiamGiaForm(Model model) {
        model.addAttribute("phieuGiamGia", new PhieuGiamGia());
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
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Integer id, Model model) {
        PhieuGiamGia voucher = phieuGiamGiaService.findByID(id);
        if (voucher != null) {
            model.addAttribute("phieuGiamGia", voucher);
            return "PhieuGiamGia/edit" ;
        }
        return "redirect:/giam-gia";
    }

    @PostMapping("/update")
    public String updateVoucher(@ModelAttribute PhieuGiamGia voucher) {
        if (phieuGiamGiaService.update(voucher)) {
            return "redirect:/giam-gia";
        }
        return "PhieuGiamGia/edit";
    }

    @GetMapping("/delete/{id}")
    public String deleteVoucher(@PathVariable Integer id) {
        phieuGiamGiaService.delete(id);
        return "redirect:/giam-gia";
    }
}