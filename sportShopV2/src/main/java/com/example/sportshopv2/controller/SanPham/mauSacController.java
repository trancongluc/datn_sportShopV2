package com.example.sportshopv2.controller.SanPham;

import com.example.sportshopv2.model.MauSac;
import com.example.sportshopv2.service.MauSacService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/mau-sac")
@RequiredArgsConstructor
public class mauSacController {
    private final MauSacService mauSacService;

    @PostMapping("/them-nhanh")
    @ResponseBody
    public ResponseEntity<String> themMauSac(@RequestBody MauSac mauSac) {
        mauSacService.addMauSac(mauSac);
        return ResponseEntity.ok("Thêm thành công");
    }

    //
    @GetMapping("")
    public ResponseEntity<List<MauSac>> getAllMauSac() {
        List<MauSac> mauSacs = mauSacService.getAllMauSac();
        return ResponseEntity.ok(mauSacs); // Trả về danh sách thương hiệu
    }
//
//    @PostMapping("/them-nhanh")
//    @ResponseBody
//    public ResponseEntity<String> themNhanh(@RequestBody ChatLieu chatLieu) {
//        chatLieu.setTrangThai("Đang hoạt động");
//        chatLieuService.addChatLieu(chatLieu);
//        return ResponseEntity.ok("Thêm thành công");
//    }
//    @PostMapping("/them-chat-lieu")
//    @ResponseBody
//    public ResponseEntity<String> themChatLieu(@RequestBody ChatLieu chatLieu) {
//        chatLieuService.addChatLieu(chatLieu);
//        return ResponseEntity.ok("Thêm thành công");
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
