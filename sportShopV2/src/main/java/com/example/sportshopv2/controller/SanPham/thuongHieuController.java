package com.example.sportshopv2.controller.SanPham;

import com.example.sportshopv2.model.ThuongHieu;
import com.example.sportshopv2.service.ThuongHieuService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/thuong-hieu")
@RequiredArgsConstructor
public class thuongHieuController {
    private final ThuongHieuService thuongHieuService;

    @GetMapping("")
    public String getAllTheLoai(@RequestParam(defaultValue = "0") int page,
                                @RequestParam(defaultValue = "5") int size, Model model) {
        Page<ThuongHieu> thuongHieuPage = thuongHieuService.getAllThuongHieu(PageRequest.of(page, size));

        model.addAttribute("thuongHieu", thuongHieuPage.getContent());
        model.addAttribute("currentPage", page); // Đảm bảo currentPage là số nguyên
        model.addAttribute("totalPages", thuongHieuPage.getTotalPages()); // Đảm bảo totalPages cũng là số nguyên
        return "SanPham/thuong-hieu"; // Trả về trang mẫu
    }
    @GetMapping("/combobox")
    public ResponseEntity<List<ThuongHieu>> getAllCbo() {
        List<ThuongHieu> brands = thuongHieuService.getAll();
        return ResponseEntity.ok(brands); // Trả về danh sách thương hiệu
    }

    @PostMapping("/them-thuong-hieu")
    @ResponseBody
    public ResponseEntity<String> themThuongHieu(@RequestBody ThuongHieu thuongHieu) {
        thuongHieuService.addThuongHieu(thuongHieu);
        return ResponseEntity.ok("Thêm thành công");
    }
    @PostMapping("/them-nhanh")
    @ResponseBody
    public ResponseEntity<String> themNhanh(@RequestBody ThuongHieu thuongHieu) {
        thuongHieu.setTrangThai("Đang hoạt động");
        thuongHieuService.addThuongHieu(thuongHieu);
        return ResponseEntity.ok("Thêm thành công");
    }
    @GetMapping("/{id}")
    @ResponseBody
    public ThuongHieu thuongHieuById(@PathVariable Integer id) {
        return thuongHieuService.getThuongHieuById(id);
    }

    @PutMapping("/{id}")
    @ResponseBody
    public ResponseEntity<ThuongHieu> updateThuongHieu(@PathVariable Integer id, @RequestBody ThuongHieu thuongHieu) {
        ThuongHieu updateThuongHieu = thuongHieuService.update(id, thuongHieu);
        if (updateThuongHieu != null) {
            return ResponseEntity.ok(updateThuongHieu);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
