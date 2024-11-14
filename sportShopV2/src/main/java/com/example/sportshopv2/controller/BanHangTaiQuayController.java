package com.example.sportshopv2.controller;

import com.example.sportshopv2.dto.SanPhamChiTietDTO;
import com.example.sportshopv2.dto.UserDTO;
import com.example.sportshopv2.model.HoaDon;
import com.example.sportshopv2.model.HoaDonChiTiet;
import com.example.sportshopv2.model.User;
import com.example.sportshopv2.repository.KhachHangRepository;
import com.example.sportshopv2.service.HoaDonChiTietService;
import com.example.sportshopv2.service.HoaDonService;
import com.example.sportshopv2.service.KhachhangService;
import com.example.sportshopv2.service.SanPhamChiTietService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/ban-hang-tai-quay")
@RequiredArgsConstructor
public class BanHangTaiQuayController {

    private final SanPhamChiTietService spctService;
    private final KhachHangRepository khRepo;
    private final HoaDonService hoaDonService;
    private final KhachhangService khachhangService;
    private final HoaDonChiTietService hdctService;

    @GetMapping("")
    public String banHangTaiQuay(Model model) {
        List<SanPhamChiTietDTO> listSPCTDto = spctService.getAllSPCT();
        List<User> listKH = khRepo.findAllKhachHang();
        model.addAttribute("spctDto", listSPCTDto);
        model.addAttribute("kh", listKH);
        return "BanHangTaiQuay/BanHangTaiQuay";
    }


    @GetMapping("/spct")
    public String getAllSPCT(@RequestParam(required = false) Integer idKH, Model model) {
        List<SanPhamChiTietDTO> listSPCTDto = spctService.getAllSPCT();
        model.addAttribute("spctDto", listSPCTDto);
        return "BanHangTaiQuay/BanHangTaiQuay";
    }

    @GetMapping("/thong-tin-kh")
    @ResponseBody
    public UserDTO getKhachHangById(@RequestParam Integer idKH) {
        return khachhangService.getKHById(idKH);
    }

    @GetMapping("/spct/{id}")
    @ResponseBody
    public SanPhamChiTietDTO getSPCTById(@PathVariable("id") Integer id) {

        return spctService.getByID(id);
    }

    @PostMapping("/tao-hoa-don")
    @ResponseBody
    public HoaDon createBill(@RequestBody HoaDon hoaDon) {
        return hoaDonService.createHoaDon(hoaDon);
    }

    @PostMapping("/tao-hoa-don-chi-tiet")
    @ResponseBody
    public List<HoaDonChiTiet> createBillDetails(@RequestBody List<HoaDonChiTiet> hdctList) {
        return hdctList.stream()
                .map(hdctService::createHDCT) // Gọi dịch vụ để tạo từng chi tiết hóa đơn
                .collect(Collectors.toList()); // Trả về danh sách các chi tiết hóa đơn đã tạo
    }
}
