

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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.UUID;

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


    @PostMapping("/save")
    public String saveVoucher(@ModelAttribute PhieuGiamGia voucher) {
        String uniqueVoucherCode = generateUniqueVoucherCode();
        voucher.setVoucherCode(uniqueVoucherCode); // Gán mã voucher vào đối tượng

        voucherService.create(voucher);
        return "redirect:/giam-gia/view";
    }
    @GetMapping("/detail/{id}")
    public String showDetail(@PathVariable Integer id, Model model, RedirectAttributes redirectAttributes) {
        if (id == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "ID không hợp lệ!");
            return "redirect:/giam-gia/view";
        }

        PhieuGiamGia giamGia = phieuGiamGiaService.findByID(id);
        if (giamGia == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "Không tìm thấy voucher!");
            return "redirect:/giam-gia/view";
        }
        model.addAttribute("giamGia", giamGia);
        return "PhieuGiamGia/detail";
    }

    @PostMapping("/update")
    public String updateGiamGia(@ModelAttribute PhieuGiamGia phieuGiamGia, RedirectAttributes redirectAttributes) {
        if (phieuGiamGia.getId() == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "ID không hợp lệ!");
            return "redirect:/giam-gia/view";
        }
        phieuGiamGiaService.update(phieuGiamGia);
        redirectAttributes.addFlashAttribute("successMessage", "Cập nhật thành công!");
        return "redirect:/giam-gia/view";
    }

    @PostMapping("/delete/{id}")
    public String deleteVoucher(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        if (phieuGiamGiaService.delete(id)) {
            redirectAttributes.addFlashAttribute("successMessage", "Xóa voucher thành công!");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Không tìm thấy voucher để xóa!");
        }
        return "redirect:/giam-gia/view";
    }
    private String generateUniqueVoucherCode() {
        // Logic để tạo mã voucher duy nhất
        // Có thể kiểm tra trong cơ sở dữ liệu để đảm bảo không trùng lặp
        String code;
        do {
            code = "VC" + UUID.randomUUID().toString().substring(0, 5).toUpperCase(); // Tạo mã ngẫu nhiên
        } while (vcRepo.existsByVoucherCode(code)); // Kiểm tra xem mã đã tồn tại chưa
        return code;
    }
}