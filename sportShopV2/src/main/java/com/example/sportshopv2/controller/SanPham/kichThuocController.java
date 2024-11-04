package com.example.sportshopv2.controller.SanPham;

import com.example.sportshopv2.model.KichThuoc;
import com.example.sportshopv2.service.KichThuocService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/kich-thuoc")
@RequiredArgsConstructor
public class kichThuocController {
    private final KichThuocService kichThuocService;

    //    @GetMapping("")
//    public String getAllKichThuoc(@RequestParam(defaultValue = "0") int page,
//                               @RequestParam(defaultValue = "5") int size, Model model) {
//        Page<KichThuoc> kichThuocs = deGiayService.getAllDeGiay(PageRequest.of(page, size));
//
//        model.addAttribute("deGiay", deGiayPage.getContent());
//        model.addAttribute("currentPage", page); // Đảm bảo currentPage là số nguyên
//        model.addAttribute("totalPages", deGiayPage.getTotalPages()); // Đảm bảo totalPages cũng là số nguyên
//        return "SanPham/de-giay"; // Trả về trang mẫu
//    }
//    @GetMapping("")
//    public ResponseEntity<List<KichThuoc>> getAll() {
//        List<KichThuoc> kichThuocs = kichThuocService.getAllKichThuoc();
//        return ResponseEntity.ok(kichThuocs); // Trả về danh sách thương hiệu
//    }
//
//    @PostMapping("/them-nhanh")
//    @ResponseBody
//    public ResponseEntity<String> themNhanh(@RequestBody KichThuoc kichThuoc) {
//        kichThuoc.setTrangThai("Đang hoạt động");
//        kichThuocService.addKichThuoc(kichThuoc);
//        return ResponseEntity.ok("Thêm thành công");
//    }

//    @PostMapping("/them-de-giay")
//    @ResponseBody
//    public ResponseEntity<String> themChatLieu(@RequestBody DeGiay deGiay) {
//        deGiayService.addDeGiay(deGiay);
//        return ResponseEntity.ok("Thêm thành công");
//    }
//
//    @GetMapping("/{id}")
//    @ResponseBody
//    public DeGiay deGiayById(@PathVariable Integer id) {
//        return deGiayService.getDeGiayById(id);
//    }
//
//    @PutMapping("/{id}")
//    @ResponseBody
//    public ResponseEntity<DeGiay> updateChatLieu(@PathVariable Integer id, @RequestBody DeGiay deGiay) {
//        DeGiay updateDeGiay = deGiayService.update(id, deGiay);
//        if (updateDeGiay != null) {
//            return ResponseEntity.ok(updateDeGiay);
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }
}
