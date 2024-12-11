package com.example.sportshopv2.controller.SanPham;

import com.example.sportshopv2.model.SanPham;
import com.example.sportshopv2.service.SanPhamChiTietService;
import com.example.sportshopv2.service.SanPhamService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/san-pham")
@RequiredArgsConstructor
public class sanPhamController {
    private final SanPhamService sanPhamService;
    private final SanPhamChiTietService spctService;

    @GetMapping("")
    public String sanPham(@RequestParam(defaultValue = "0") int page,
                          @RequestParam(defaultValue = "5") int size,
                          @RequestParam(value = "keyword", required = false) String keyword,
                          @RequestParam(value = "status", required = false) String status,
                          Model model) {
        // Tạo Pageable từ các tham số page và size
        Pageable pageable = PageRequest.of(page, size);

        // Kiểm tra nếu có từ khóa tìm kiếm và trạng thái, nếu có thì gọi phương thức tìm kiếm
        Page<SanPham> sanPhamPage;
        if (keyword != null && !keyword.isEmpty() && (status != null && !status.equals("all"))) {
            // Tìm kiếm theo keyword và status
            sanPhamPage = sanPhamService.searchSP(keyword, status, pageable);
        } else if (keyword != null && !keyword.isEmpty()) {
            // Tìm kiếm chỉ theo keyword
            sanPhamPage = sanPhamService.searchSP(keyword, "all", pageable);
        } else if (status != null && !status.equals("all")) {
            // Tìm kiếm chỉ theo status
            sanPhamPage = sanPhamService.searchSP("", status, pageable);
        } else {
            // Lấy tất cả sản phẩm nếu không có từ khóa và trạng thái
            sanPhamPage = sanPhamService.getAllSanPham(pageable);
        }

        // Kiểm tra nếu không có kết quả tìm kiếm
        boolean isNoData = sanPhamPage.getContent().isEmpty();

        // Thêm các dữ liệu cần thiết vào model
        model.addAttribute("sanPham", sanPhamPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", sanPhamPage.getTotalPages());
        model.addAttribute("keyword", keyword);  // Giữ lại keyword trong form
        model.addAttribute("status", status);    // Giữ lại status trong form
        model.addAttribute("isNoData", isNoData);  // Thêm thông tin nếu không có dữ liệu

        return "SanPham/san-pham"; // Trả về trang mẫu
    }

    @GetMapping("/list")
    @ResponseBody
    public List<SanPham> getAllSP() {
        return sanPhamService.findAllSanPham();
    }


    @GetMapping("/soLuong-spct")
    @ResponseBody
    public Integer soLuongSPCT(@RequestParam Integer idSP) {
        return spctService.tongSoLuongSP(idSP);
    }

    @GetMapping("/{idSP}")
    @ResponseBody
    public SanPham getSPDetail(@PathVariable Integer idSP) {
        return sanPhamService.getSanPhamById(idSP);
    }

    @PostMapping("/them-san-pham")
    @ResponseBody
    public ResponseEntity<SanPham> themChatLieu(@RequestBody SanPham sanPham) {

        return ResponseEntity.ok(sanPhamService.addSanPham(sanPham));
    }

    @PutMapping("/update/{id}")
    @ResponseBody
    public ResponseEntity<SanPham> updateChatLieu(@PathVariable Integer id, @RequestBody SanPham sanPham) {
        SanPham updateSP = sanPhamService.update(id, sanPham);
        if (updateSP != null) {
            return ResponseEntity.ok(updateSP);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
