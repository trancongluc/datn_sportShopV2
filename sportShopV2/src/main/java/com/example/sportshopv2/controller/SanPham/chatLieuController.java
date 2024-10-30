package com.example.sportshopv2.controller.SanPham;

import com.example.sportshopv2.model.ChatLieu;
import com.example.sportshopv2.service.ChatLieuService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/chat-lieu")
@RequiredArgsConstructor
public class chatLieuController {
    private final ChatLieuService chatLieuService;

    @GetMapping("")
    public String theLoai(@RequestParam(defaultValue = "0") int page,
                          @RequestParam(defaultValue = "5") int size, Model model) {
        Page<ChatLieu> chatLieuPage = chatLieuService.getAllChatLieu(PageRequest.of(page, size));

        model.addAttribute("cl", chatLieuPage.getContent());
        model.addAttribute("currentPage", page); // Đảm bảo currentPage là số nguyên
        model.addAttribute("totalPages", chatLieuPage.getTotalPages()); // Đảm bảo totalPages cũng là số nguyên
        return "SanPham/chat-lieu"; // Trả về trang mẫu
    }


    @PostMapping("/them-chat-lieu")
    @ResponseBody
    public ResponseEntity<String> themChatLieu(@RequestBody ChatLieu chatLieu) {
        chatLieuService.addChatLieu(chatLieu);
        return ResponseEntity.ok("Thêm thành công");
    }

    @GetMapping("/{id}")
    @ResponseBody
    public ChatLieu chatLieuById(@PathVariable Integer id) {
        return chatLieuService.getChatLieuById(id);
    }

    @PutMapping("/{id}")
    @ResponseBody
    public ResponseEntity<ChatLieu> updateChatLieu(@PathVariable Integer id, @RequestBody ChatLieu chatLieu) {
        ChatLieu updatedChatLieu = chatLieuService.update(id, chatLieu);
        if (updatedChatLieu != null) {
            return ResponseEntity.ok(updatedChatLieu);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
