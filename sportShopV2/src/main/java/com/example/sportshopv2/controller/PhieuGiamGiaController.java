

package com.example.sportshopv2.controller;

import com.example.sportshopv2.model.PhieuGiamGia;
import com.example.sportshopv2.repository.PhieuGiamGiaChiTietResponsitory;
import com.example.sportshopv2.repository.PhieuGiamGiaResponsitory;
import com.example.sportshopv2.service.PhieuGiamGiaService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
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
        List<PhieuGiamGia> vouchers = vcRepo.findAll(Sort.by(Sort.Direction.DESC, "createAt"));
        for (PhieuGiamGia voucher : vouchers) {
            if (voucher.getEndDate() != null && voucher.getEndDate().isBefore(LocalDateTime.now())) {
                voucher.setStatus("Hết hạn");
                phieuGiamGiaService.update(voucher); // Cập nhật vào cơ sở dữ liệu ngay lập tức
            }
        }
        model.addAttribute("listVC", vouchers);
        return "PhieuGiamGia/giamGia";
    }

    @GetMapping("/add-giam-gia")
    public String showAddGiamGiaForm(Model model) {
        model.addAttribute("phieuGiamGia", new PhieuGiamGia());
        return "PhieuGiamGia/add";
    }

    @PostMapping("/save")
    public String saveVoucher(@ModelAttribute PhieuGiamGia voucher, RedirectAttributes redirectAttributes) {
        if (!isValidVoucher(voucher, redirectAttributes)) {
            return "redirect:/giam-gia/add-giam-gia";
        }
        if (voucher.getEndDate() != null && voucher.getEndDate().isBefore(LocalDateTime.now())) {
            voucher.setStatus("Hết hạn");
        } else {
            voucher.setStatus("Đang diễn ra"); // Trạng thái mặc định nếu chưa hết hạn
        }

        String uniqueVoucherCode = generateUniqueVoucherCode();
        voucher.setVoucherCode(uniqueVoucherCode);
        voucher.setCreateAt(LocalDateTime.now());
        voucherService.create(voucher);

//        checkAndUpdateVoucherStatus(voucher);
        return "redirect:/giam-gia/view";
    }
//    public void checkAndUpdateVoucherStatus(PhieuGiamGia voucher) {
//        LocalDateTime now = LocalDateTime.now();
//        if (voucher.getEndDate() != null && voucher.getEndDate().isBefore(now)) {
//            voucher.setStatus("Hết hạn");  // Cập nhật trạng thái phiếu giảm giá
//            phieuGiamGiaService.update(voucher);  // Lưu lại vào cơ sở dữ liệu
//        }
//    }
    @PostMapping("/update")
    public String updateGiamGia(@ModelAttribute PhieuGiamGia phieuGiamGia, RedirectAttributes redirectAttributes) {
        if (!isValidVoucher(phieuGiamGia, redirectAttributes)) {
            return "redirect:/giam-gia/detail/" + phieuGiamGia.getId();
        }
        if (phieuGiamGia.getEndDate() != null && phieuGiamGia.getEndDate().isBefore(LocalDateTime.now())) {
            phieuGiamGia.setStatus("Hết hạn");
        } else {
            phieuGiamGia.setStatus("Đang diễn ra");
        }
        phieuGiamGiaService.update(phieuGiamGia);
        redirectAttributes.addFlashAttribute("successMessage", "Cập nhật thành công!");

        return "redirect:/giam-gia/view";
    }

    private boolean isValidVoucher(PhieuGiamGia voucher, RedirectAttributes redirectAttributes) {

        boolean isValid = true;

        LocalDateTime startDate = voucher.getStartDate();
        LocalDateTime endDate = voucher.getEndDate();


        if (startDate != null && endDate != null) {
            if (endDate.isBefore(startDate)) {
                redirectAttributes.addFlashAttribute("errorEndDate", "Ngày kết thúc không được trước ngày bắt đầu!");
                isValid = false;
            }
        } else {
            if (startDate == null) {
                redirectAttributes.addFlashAttribute("errorStartDate", "Ngày bắt đầu không được để trống!");
                isValid = false;
            }
            if (endDate == null) {
                redirectAttributes.addFlashAttribute("errorEndDate", "Ngày kết thúc không được để trống!");
                isValid = false;
            }
        }


        if (voucher.getQuantity() < 0) {
            redirectAttributes.addFlashAttribute("errorQuantity", "Số lượng không được âm!");
            isValid = false;
        }


        BigDecimal minimumValue = voucher.getMinimumValue();
        if (minimumValue != null && minimumValue.compareTo(BigDecimal.ZERO) < 0) {
            redirectAttributes.addFlashAttribute("errorMinimumValue", "Giá trị không được âm!");
            isValid = false;
        }

        BigDecimal discountAmount = voucher.getDiscountValue(); // Assuming getter is defined
        if (discountAmount != null && discountAmount.compareTo(BigDecimal.ZERO) < 0) {
            redirectAttributes.addFlashAttribute("errorDiscountAmount", "Số tiền giảm không được âm!");
            isValid = false;
        }
        return isValid;
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


    @PutMapping("/update")
    @ResponseBody
    public boolean updateSoLuongGiamGia(@RequestBody PhieuGiamGia phieuGiamGia) {
        return phieuGiamGiaService.update(phieuGiamGia);
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