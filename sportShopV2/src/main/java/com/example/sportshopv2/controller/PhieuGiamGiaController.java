

package com.example.sportshopv2.controller;

import com.example.sportshopv2.model.PhieuGiamGia;
import com.example.sportshopv2.repository.PhieuGiamGiaChiTietResponsitory;
import com.example.sportshopv2.repository.PhieuGiamGiaResponsitory;
import com.example.sportshopv2.service.PhieuGiamGiaService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/giam-gia")
@RequiredArgsConstructor
public class PhieuGiamGiaController {

    @Autowired
    private PhieuGiamGiaResponsitory vcRepo;

    @Autowired
    private PhieuGiamGiaChiTietResponsitory vcctRepo;

    @Autowired
    private PhieuGiamGiaService voucherService;

    @Autowired
    private PhieuGiamGiaService phieuGiamGiaService;
    @GetMapping("/view")
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
        return "redirect:/view";
    }
    @GetMapping("/detail/{id}")
    public String showDetail(@PathVariable Long id, Model model) {
//        PhieuGiamGia giamGia = phieuGiamGiaService.findByID(id);
//        model.addAttribute("giamGia", giamGia);
        return "detail"; // Trả về tên của template detail.html
    }

    @PostMapping("/update")
    public String updateGiamGia(@ModelAttribute PhieuGiamGia giamGia) {
//        phieuGiamGiaService.save(giamGia); // Lưu đối tượng đã cập nhật
        return "redirect:/giam-gia"; // Chuyển hướng về danh sách
    }

    @GetMapping("/delete/{id}")
    public String deleteVoucher(@PathVariable Integer id) {
        phieuGiamGiaService.delete(id);
        return "redirect:/giam-gia";
    }
}