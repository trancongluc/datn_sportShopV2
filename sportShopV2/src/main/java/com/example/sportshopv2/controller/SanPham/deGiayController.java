package com.example.sportshopv2.controller.SanPham;

import com.example.sportshopv2.model.DeGiay;
import com.example.sportshopv2.service.DeGiayService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/de-giay")
@RequiredArgsConstructor
public class deGiayController {
    private final DeGiayService deGiayService;

    @GetMapping("")
    public String getAllCoGiay(@RequestParam(defaultValue = "0") int page,
                               @RequestParam(defaultValue = "5") int size, Model model) {
        Page<DeGiay> deGiayPage = deGiayService.getAllDeGiay(PageRequest.of(page, size));

        model.addAttribute("deGiay", deGiayPage.getContent());
        model.addAttribute("currentPage", page); // Đảm bảo currentPage là số nguyên
        model.addAttribute("totalPages", deGiayPage.getTotalPages()); // Đảm bảo totalPages cũng là số nguyên
        return "SanPham/de-giay"; // Trả về trang mẫu
    }

    @GetMapping("/combobox")
    public ResponseEntity<List<DeGiay>> getAllCbo() {
        List<DeGiay> deGiays = deGiayService.getAll();
        return ResponseEntity.ok(deGiays); // Trả về danh sách thương hiệu
    }

    @PostMapping("/them-nhanh")
    @ResponseBody
    public ResponseEntity<String> themNhanh(@RequestBody DeGiay deGiay) {
        deGiay.setTrangThai("Đang hoạt động");
        deGiayService.addDeGiay(deGiay);
        return ResponseEntity.ok("Thêm thành công");
    }

    @PostMapping("/them-de-giay")
    @ResponseBody
    public ResponseEntity<String> themChatLieu(@RequestBody DeGiay deGiay) {
        deGiayService.addDeGiay(deGiay);
        return ResponseEntity.ok("Thêm thành công");
    }

    @GetMapping("/{id}")
    @ResponseBody
    public DeGiay deGiayById(@PathVariable Integer id) {
        return deGiayService.getDeGiayById(id);
    }

    @PutMapping("/{id}")
    @ResponseBody
    public ResponseEntity<DeGiay> updateChatLieu(@PathVariable Integer id, @RequestBody DeGiay deGiay) {
        DeGiay updateDeGiay = deGiayService.update(id, deGiay);
        if (updateDeGiay != null) {
            return ResponseEntity.ok(updateDeGiay);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
