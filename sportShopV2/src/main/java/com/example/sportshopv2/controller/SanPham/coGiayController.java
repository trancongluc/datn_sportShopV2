package com.example.sportshopv2.controller.SanPham;

import com.example.sportshopv2.model.CoGiay;
import com.example.sportshopv2.service.CoGiayService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/co-giay")
@RequiredArgsConstructor
public class coGiayController {
    private final CoGiayService coGiayService;

    @GetMapping("")
    public String getAllCoGiay(@RequestParam(defaultValue = "0") int page,
                               @RequestParam(defaultValue = "5") int size, Model model) {
        Page<CoGiay> coGiayPage = coGiayService.getAllCoGiay(PageRequest.of(page, size));

        model.addAttribute("coGiay", coGiayPage.getContent());
        model.addAttribute("currentPage", page); // Đảm bảo currentPage là số nguyên
        model.addAttribute("totalPages", coGiayPage.getTotalPages()); // Đảm bảo totalPages cũng là số nguyên
        return "SanPham/co-giay"; // Trả về trang mẫu
    }

    @GetMapping("/combobox")
    public ResponseEntity<List<CoGiay>> getAllCoGiay() {
        List<CoGiay> coGiays = coGiayService.getAll();
        return ResponseEntity.ok(coGiays); // Trả về danh sách thương hiệu
    }
    @PostMapping("/them-nhanh")
    @ResponseBody
    public ResponseEntity<String> themNhanh(@RequestBody CoGiay coGiay) {
        coGiay.setTrangThai("Đang hoạt động");
        coGiayService.addCoGiay(coGiay);
        return ResponseEntity.ok("Thêm thành công");
    }
    @PostMapping("/them-co-giay")
    @ResponseBody
    public ResponseEntity<String> themChatLieu(@RequestBody CoGiay coGiay) {
        coGiayService.addCoGiay(coGiay);
        return ResponseEntity.ok("Thêm thành công");
    }

    @GetMapping("/{id}")
    @ResponseBody
    public CoGiay coGiayById(@PathVariable Integer id) {
        return coGiayService.getCoGiayById(id);
    }

    @PutMapping("/{id}")
    @ResponseBody
    public ResponseEntity<CoGiay> updateChatLieu(@PathVariable Integer id, @RequestBody CoGiay coGiay) {
        CoGiay updateCoGiay = coGiayService.update(id, coGiay);
        if (updateCoGiay != null) {
            return ResponseEntity.ok(updateCoGiay);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
