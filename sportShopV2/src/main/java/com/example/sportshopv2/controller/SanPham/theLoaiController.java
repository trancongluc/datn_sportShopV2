package com.example.sportshopv2.controller.SanPham;

import com.example.sportshopv2.model.TheLoai;
import com.example.sportshopv2.service.TheLoaiService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/the-loai")
@RequiredArgsConstructor
public class theLoaiController {
    private final TheLoaiService theLoaiService;

    @GetMapping("")
    public String getAllTheLoai(@RequestParam(defaultValue = "0") int page,
                               @RequestParam(defaultValue = "5") int size, Model model) {
        Page<TheLoai> theLoaiPage = theLoaiService.getAllTheLoai(PageRequest.of(page, size));

        model.addAttribute("theLoai", theLoaiPage.getContent());
        model.addAttribute("currentPage", page); // Đảm bảo currentPage là số nguyên
        model.addAttribute("totalPages", theLoaiPage.getTotalPages()); // Đảm bảo totalPages cũng là số nguyên
        return "SanPham/the-loai"; // Trả về trang mẫu
    }
    @GetMapping("/combobox")
    public ResponseEntity<List<TheLoai>> getAllCbo() {
        List<TheLoai> theLoais = theLoaiService.getAll();
        return ResponseEntity.ok(theLoais); // Trả về danh sách thương hiệu
    }

    @PostMapping("/them-the-loai")
    @ResponseBody
    public ResponseEntity<String> themChatLieu(@RequestBody TheLoai theLoai) {
        theLoaiService.addTheLoai(theLoai);
        return ResponseEntity.ok("Thêm thành công");
    }
    @PostMapping("/them-nhanh")
    @ResponseBody
    public ResponseEntity<String> themNhanh(@RequestBody TheLoai theLoai) {
        theLoai.setTrangThai("Đang hoạt động");
        theLoaiService.addTheLoai(theLoai);
        return ResponseEntity.ok("Thêm thành công");
    }
    @GetMapping("/{id}")
    @ResponseBody
    public TheLoai deGiayById(@PathVariable Integer id) {
        return theLoaiService.getTheLoaiById(id);
    }

    @PutMapping("/{id}")
    @ResponseBody
    public ResponseEntity<TheLoai> updateChatLieu(@PathVariable Integer id, @RequestBody TheLoai theLoai) {
        TheLoai updateTheLoai = theLoaiService.update(id, theLoai);
        if (updateTheLoai != null) {
            return ResponseEntity.ok(updateTheLoai);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
