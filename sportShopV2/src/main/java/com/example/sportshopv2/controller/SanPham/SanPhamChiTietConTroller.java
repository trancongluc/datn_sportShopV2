package com.example.sportshopv2.controller.SanPham;

import com.example.sportshopv2.dto.SanPhamChiTietDTO;
import com.example.sportshopv2.model.KichThuoc;
import com.example.sportshopv2.model.MauSac;
import com.example.sportshopv2.model.SanPham;
import com.example.sportshopv2.model.SanPhamChiTiet;
import com.example.sportshopv2.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/san-pham-chi-tiet")
@RequiredArgsConstructor
public class SanPhamChiTietConTroller {
    private final ChatLieuService chatLieuService;
    private final CoGiayService coGiayService;
    private final DeGiayService deGiayService;
    private final KichThuocService kichThuocService;
    private final MauSacService mauSacService;
    private final SanPhamService sanPhamService;
    private final TheLoaiService theLoaiService;
    private final ThuongHieuService thuongHieuService;
    private final SanPhamChiTietService spctService;

    @ModelAttribute
    public void addCommonAttributes(Model model) {
        model.addAttribute("cl", chatLieuService.getAll());
        model.addAttribute("cg", coGiayService.getAll());
        model.addAttribute("th", thuongHieuService.getAll());
        model.addAttribute("dg", deGiayService.getAll());
        model.addAttribute("tl", theLoaiService.getAll());
        model.addAttribute("kt", kichThuocService.getAllKichThuoc());
        model.addAttribute("ms", mauSacService.getAllMauSac());
    }

    @GetMapping("")
    public String sanPhamChiTiet(Model model) {
        return "SanPham/them-san-pham";
    }
    @GetMapping("/update/{idSP}")
    public String updateSpctByIdSP(
            @PathVariable("idSP") Integer idSP,
            Model model
    ) {
        List<SanPhamChiTietDTO> listSPCTDTO = spctService.getListSPCTByIdSP(idSP);
        List<SanPhamChiTiet> listSPCT = spctService.findAllByIdSP(idSP);
        SanPham sanPham = sanPhamService.findAllSanPhamById(idSP);

        if (!listSPCT.isEmpty()) {
            SanPhamChiTiet spct = listSPCT.get(0); // Lấy giá trị chung từ bản ghi đầu tiên
            model.addAttribute("thuongHieuId", spct.getIdThuongHieu());
            model.addAttribute("chatLieuId", spct.getIdChatLieu());
            model.addAttribute("gioiTinh", spct.getGioiTinh());
            model.addAttribute("coGiayId", spct.getIdCoGiay());
            model.addAttribute("deGiayId", spct.getIdDeGiay());
            model.addAttribute("theLoaiId",spct.getIdTheLoai());
            model.addAttribute("moTa",spct.getMoTa());

        }
        // Lấy danh sách ID kích cỡ và màu sắc duy nhất
        List<Integer> sizeIds = listSPCT.stream()
                .map(SanPhamChiTiet::getIdKichThuoc)
                .distinct()
                .collect(Collectors.toList());

        List<Integer> colorIds = listSPCT.stream()
                .map(SanPhamChiTiet::getIdMauSac)
                .distinct()
                .collect(Collectors.toList());

        // Lấy chi tiết kích cỡ và màu sắc từ database
        List<KichThuoc> uniqueSizes = kichThuocService.findAllByIds(sizeIds);
        List<MauSac> uniqueColors = mauSacService.findAllByIds(colorIds);
        model.addAttribute("ktNotSP", kichThuocService.getSizesNotInProduct(idSP));
        model.addAttribute("msNotSP", mauSacService.getColorsNotInProduct(idSP));
        model.addAttribute("sanPham", sanPham);
        model.addAttribute("spDetail", listSPCT);
        model.addAttribute("spctDto", listSPCTDTO);
        model.addAttribute("sizes", uniqueSizes);
        model.addAttribute("colors", uniqueColors);
        return "SanPham/update-san-pham";
    }
  /*  @GetMapping("/product/{productId}/sizes-not-in")
    @ResponseBody
    public List<KichThuoc> getSizesNotInProduct(@PathVariable Integer productId) {
        List<KichThuoc> sizes = kichThuocService.getSizesNotInProduct(productId);
        return sizes;
    }*/
    @GetMapping("/{idSP}")
    public String getSPCTByIdSP(
            @PathVariable("idSP") Integer idSP,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
            , Model model) {
        Page<SanPhamChiTietDTO> listSPCT = spctService.getSPCTByIdSP(idSP, PageRequest.of(page, size));
        model.addAttribute("spct", listSPCT.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", listSPCT.getTotalPages());
        return "SanPham/san-pham-chi-tiet";
    }
    @PutMapping("/update/{id}")
    @ResponseBody
    public ResponseEntity<?> updateSanPhamChiTiet(@PathVariable Integer id, @RequestBody SanPhamChiTiet spct) {
        try {
            SanPhamChiTiet updatedSanPhamChiTiet = spctService.updateSanPhamChiTiet(id, spct);
            return ResponseEntity.ok(updatedSanPhamChiTiet);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Có lỗi xảy ra khi cập nhật sản phẩm chi tiết");
        }
    }
    @PostMapping("/them-san-pham-chi-tiet")
    @ResponseBody
    public ResponseEntity<SanPhamChiTiet> themChatLieu(@RequestBody SanPhamChiTiet spct) {
        return ResponseEntity.ok(spctService.addSPCT(spct));
    }

    @GetMapping("/thong-tin-spct/{id}")
    @ResponseBody
    public SanPhamChiTiet thongTinSPCT(@PathVariable("id") Integer id) {
        return spctService.findSPCTById(id);
    }
    @PutMapping("/cap-nhat-so-luong/{idSPCT}")
    @ResponseBody
    public ResponseEntity<String> updateProductQuantity(@PathVariable("idSPCT") Integer idSPCT, @RequestParam Integer soLuongNew) {
        boolean isUpdated = spctService.capNhatSoLuongSPCT(idSPCT, soLuongNew);
        if (isUpdated) {
            return ResponseEntity.ok("Cập nhật số lượng thành công!");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy sản phẩm hoặc cập nhật thất bại.");
        }
    }

}
