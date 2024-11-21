package com.example.sportshopv2.controller;

import com.example.sportshopv2.model.ChatLieu;
import com.example.sportshopv2.model.DotGiamGia;
import com.example.sportshopv2.model.PhieuGiamGia;
import com.example.sportshopv2.model.SanPham;
import com.example.sportshopv2.repository.*;
import com.example.sportshopv2.service.DotGiamGiaService;
import com.example.sportshopv2.service.impl.DotGiamGiaServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.UUID;

@Controller
@RequestMapping("/dot-giam-gia")
public class DotGiamGiaController {
    @Autowired
    private PhieuGiamGiaResponsitory vcRepo;

    @Autowired
    private PhieuGiamGiaChiTietResponsitory vcctRepo;

    @Autowired
    private DotGiamGiaRepo dotGiamGiaRepo;

    @Autowired
    private DotGiamGiaServiceImp dotGiamGiaServiceImp;

    @Autowired
    private SanPhamRepository sanPhamRep;

    @Autowired
    private SanPhamChiTietRepository SPCTRep;

    @GetMapping("/view")
    public String DotGiamGia(Model model) {
        model.addAttribute("listDotGiamGia", dotGiamGiaRepo.findAll());
        return "DotGiamGia/dotGiamGia";
    }

    @GetMapping("/add-dot-giam-gia")
    public String showAddGiamGiaForm(Model model) {
        model.addAttribute("dotGiamGia", new DotGiamGia());
        model.addAttribute("listSanPham", sanPhamRep.findAll());
        model.addAttribute("listSPCT", SPCTRep.findAll());
        return "DotGiamGia/addDotGiamGia";
    }


    @PostMapping("/save")
    public String saveDotGiamGia(@ModelAttribute DotGiamGia dotGiamGia) {
        String uniqueVoucherCode = generateUniqueVoucherCode();
        dotGiamGia.setSaleCode(uniqueVoucherCode);

        dotGiamGiaRepo.save(dotGiamGia);
        return "redirect:/dot-giam-gia/view";
    }

    @GetMapping("/detail/{id}")
    public String showDetail(@PathVariable Integer id, Model model, RedirectAttributes redirectAttributes) {
        model.addAttribute("listSanPham", sanPhamRep.findAll());
        if (id == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "ID không hợp lệ!");
            return "redirect:/dot-giam-gia/view";
        }

        DotGiamGia dotGiamGia = dotGiamGiaRepo.findById(id).orElse(null);
        if (dotGiamGia == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "Không tìm thấy đợt giảm giá!");
            return "redirect:/dot-giam-gia/view";
        }
        model.addAttribute("dotGiamGia", dotGiamGia);
        return "DotGiamGia/detail";
    }

    @PostMapping("/update")
    public String updateDotGiamGia(@ModelAttribute DotGiamGia dotGiamGia, RedirectAttributes redirectAttributes) {
        if (dotGiamGia.getId() == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "ID không hợp lệ!");
            return "redirect:/dot-giam-gia/view";
        }
        dotGiamGiaRepo.save(dotGiamGia);
        redirectAttributes.addFlashAttribute("successMessage", "Cập nhật thành công!");
        return "redirect:/dot-giam-gia/view";
    }

    @PostMapping("/delete/{id}")
    public String deleteDotGiamGia(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        if (dotGiamGiaRepo.existsById(id)) {
            dotGiamGiaRepo.deleteById(id);
            redirectAttributes.addFlashAttribute("successMessage", "Xóa đợt giảm giá thành công!");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Không tìm thấy đợt giảm giá để xóa!");
        }
        return "redirect:/dot-giam-gia/view";
    }

    private String generateUniqueVoucherCode() {
        String code;
        do {
            code = "Sale" + UUID.randomUUID().toString().substring(0, 5).toUpperCase();
        } while (dotGiamGiaRepo.existsBySaleCode(code));
        return code;
    }
}
