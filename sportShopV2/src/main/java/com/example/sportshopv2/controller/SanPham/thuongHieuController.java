package com.example.sportshopv2.controller.SanPham;

import com.example.sportshopv2.model.TheLoai;
import com.example.sportshopv2.model.ThuongHieu;
import com.example.sportshopv2.service.TheLoaiService;
import com.example.sportshopv2.service.ThuongHieuService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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


    @PostMapping("/them-thuong-hieu")
    @ResponseBody
    public ResponseEntity<String> themThuongHieu(@RequestBody ThuongHieu thuongHieu) {
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
