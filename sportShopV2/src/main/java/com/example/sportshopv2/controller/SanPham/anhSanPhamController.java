package com.example.sportshopv2.controller.SanPham;

import com.example.sportshopv2.model.AnhSanPham;
import com.example.sportshopv2.service.AnhService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/anh-san-pham")
@RequiredArgsConstructor
public class anhSanPhamController {
    private final AnhService anhService;
    @PostMapping("/upload")
    @ResponseBody
    public ResponseEntity<?> uploadImages(@RequestBody List<AnhSanPham> images) {
        anhService.saveImages(images);
        return ResponseEntity.ok("Images uploaded successfully!");
    }
}
