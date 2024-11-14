package com.example.sportshopv2.controller.SanPham;

import com.example.sportshopv2.model.SanPham;
import com.example.sportshopv2.service.SanPhamService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/san-pham")
@RequiredArgsConstructor
public class sanPhamController {
    private final SanPhamService sanPhamService;

    @GetMapping("")
    public String sanPham(@RequestParam(defaultValue = "0") int page,
                          @RequestParam(defaultValue = "5") int size, Model model) {
        Page<SanPham> sanPhamPage = sanPhamService.getAllSanPham(PageRequest.of(page, size));

        model.addAttribute("sanPham", sanPhamPage.getContent());
        model.addAttribute("currentPage", page); // Đảm bảo currentPage là số nguyên
        model.addAttribute("totalPages", sanPhamPage.getTotalPages()); // Đảm bảo totalPages cũng là số nguyên
        return "SanPham/san-pham"; // Trả về trang mẫu
    }

    @PostMapping("/them-san-pham")
    @ResponseBody
    public ResponseEntity<SanPham> themChatLieu(@RequestBody SanPham sanPham) {

        return ResponseEntity.ok(sanPhamService.addSanPham(sanPham));
    }
//    @GetMapping("/them-san-pham")
//    public String themSanPham() {
//        return "SanPham/them-san-pham";
//    }
//
//    @GetMapping("/{id}")
//    @ResponseBody
//    public ChatLieu chatLieuById(@PathVariable Integer id) {
//        return chatLieuService.getChatLieuById(id);
//    }
//
//    @PutMapping("/{id}")
//    @ResponseBody
//    public ResponseEntity<ChatLieu> updateChatLieu(@PathVariable Integer id, @RequestBody ChatLieu chatLieu) {
//        ChatLieu updatedChatLieu = chatLieuService.update(id, chatLieu);
//        if (updatedChatLieu != null) {
//            return ResponseEntity.ok(updatedChatLieu);
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }
}
