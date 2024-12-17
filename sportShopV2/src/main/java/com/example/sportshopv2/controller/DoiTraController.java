package com.example.sportshopv2.controller;

import com.example.sportshopv2.dto.HoaDonChiTietDTO;
import com.example.sportshopv2.model.HoaDon;
import com.example.sportshopv2.model.HoaDonChiTiet;
import com.example.sportshopv2.repository.HoaDonChiTietRepo;
import com.example.sportshopv2.repository.HoaDonRepo;
import com.example.sportshopv2.repository.SanPhamChiTietRepository;
import com.example.sportshopv2.repository.SanPhamRepository;

import com.example.sportshopv2.service.HoaDonChiTietService;
import com.example.sportshopv2.service.HoaDonService;
import com.example.sportshopv2.service.KhachhangService;
import com.example.sportshopv2.service.SanPhamChiTietService;

import com.example.sportshopv2.service.impl.HoaDonServiceImp;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/doi-tra")
public class DoiTraController {

    @Autowired
    private HoaDonServiceImp hoaDonServiceImp;
    @Autowired
    private HoaDonChiTietRepo hdctRepo;
    @Autowired
    private HoaDonChiTietService hdctService;
    @Autowired
    private HoaDonService hdService;

    @GetMapping("/view")
    public String DoiTraView() {

        return "DoiTra/DoiTra";
    }

    @GetMapping("/detail")
    public String getHoaDonDetail(@RequestParam("tenHoaDon") String tenHoaDon, Model model) {

        HoaDon hoaDon = hoaDonServiceImp.getBillDetailByBillCode(tenHoaDon);
        if (hoaDon == null) {
            model.addAttribute("errorMessage", "Không tìm thấy hóa đơn với mã: " + tenHoaDon);
            return "DoiTra/DoiTra";
        } else {
            model.addAttribute("hoaDon", hoaDon);
        }
        return "DoiTra/DoiTraChiTiet";
    }

    /* @GetMapping("/detail")
    @ResponseBody
    public HoaDon getHoaDonDetail2(@RequestParam("tenHoaDon") String tenHoaDon) {

       return hoaDonServiceImp.getBillDetailByBillCode(tenHoaDon);

    }*/
    @PutMapping("/updateHD/{id}")
    @ResponseBody
    public ResponseEntity<?> updateHoaDon(
            @PathVariable("id") Integer idHD,
            @RequestBody HoaDon updatedHoaDon) {
        try {
            // Gọi service để cập nhật hóa đơn
            hoaDonServiceImp.updateHoaDon(idHD, updatedHoaDon);

            // Trả về đối tượng JSON
            return ResponseEntity.ok(Map.of("status", "success", "message", "Hóa đơn đã được cập nhật thành công."));
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("status", "error", "message", "Không tìm thấy hóa đơn với mã: " + idHD));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("status", "error", "message", "Đã xảy ra lỗi: " + ex.getMessage()));
        }
    }

    @PutMapping("/updateHDCT/{id}")
    @ResponseBody
    public ResponseEntity<?> updateHoaDonCT(
            @PathVariable("id") Integer idHDCT,
            @RequestBody HoaDonChiTiet updateHDCT) {
        try {
            // Gọi service để cập nhật hóa đơn
            hdctService.updateHoaDonCT(idHDCT, updateHDCT);

            // Trả về đối tượng JSON
            return ResponseEntity.ok(Map.of("status", "success", "message", "Hóa đơn đã được cập nhật thành công."));
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("status", "error", "message", "Không tìm thấy hóa đơn với mã: " + idHDCT));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("status", "error", "message", "Đã xảy ra lỗi: " + ex.getMessage()));
        }
    }

    @GetMapping("/thong-tin/")
    @ResponseBody
    public List<HoaDonChiTiet> hdctDetail(@RequestParam("idHDCT") List<Integer> idHDCTs) {
        return hdctRepo.findAllById(idHDCTs);
    }

    @PutMapping("/update-hoa-don/{idHD}")
    @ResponseBody
    public HoaDon updateBill(@PathVariable("idHD") Integer idHD,
                             @RequestBody HoaDon hoaDon) {
        try {
            return hdService.updateHoaDonHoanTra(idHD, hoaDon);
        } catch (Exception e) {
            // Xử lý lỗi (có thể log lỗi và trả về thông báo lỗi)
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Cập nhật hóa đơn không thành công", e);
        }
    }
}