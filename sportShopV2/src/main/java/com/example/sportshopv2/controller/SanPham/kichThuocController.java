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


    @PostMapping("/them-nhanh")
    @ResponseBody
    public ResponseEntity<String> themKichThuoc(@RequestBody KichThuoc kichThuoc) {
        kichThuocService.addKichThuoc(kichThuoc);
        return ResponseEntity.ok("Thêm thành công");
    }

    //
    @GetMapping("")
    public ResponseEntity<List<KichThuoc>> getAllKichThuoc() {
        List<KichThuoc> kichThuocs = kichThuocService.getAllKichThuoc();
        return ResponseEntity.ok(kichThuocs); // Trả về danh sách thương hiệu
    }
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
