package com.example.sportshopv2.controller;

import com.example.sportshopv2.dto.SanPhamChiTietDTO;
import com.example.sportshopv2.dto.UserDTO;
import com.example.sportshopv2.model.*;
import com.example.sportshopv2.repository.*;
import com.example.sportshopv2.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/ban-hang-tai-quay")
@RequiredArgsConstructor
public class BanHangTaiQuayController {

    private final SanPhamChiTietService spctService;
    private final KhachHangRepository khRepo;
    private final HoaDonService hoaDonService;
    private final HoaDonRepo hdRepo;
    private final KhachhangService khachhangService;
    private final HoaDonChiTietService hdctService;
    private final TaiKhoanService tkService;
    private final NguoiDungRepo ndRepo;
    private final PhieuGiamGiaService voucherService;
    private final PhieuGiamGiaChiTietResponsitory voucherDetailService;
    private final AddressRepo addressRepo;
    @GetMapping("")
    public String banHangTaiQuay(Model model) {
        List<SanPhamChiTietDTO> listSPCTDto = spctService.getAllSPCT();
        List<User> listKH = khRepo.findAllKhachHang();

        model.addAttribute("spctDto", listSPCTDto);
        model.addAttribute("kh", listKH);

        return "BanHangTaiQuay/BanHangTaiQuay";
    }
    @GetMapping("/cboKH")
    @ResponseBody
    public ResponseEntity<List<User>> timKhachHang(@RequestParam String keyword) {
        List<User> danhSachKhachHang = khRepo.findByTenHoacSoDienThoai(keyword);
        return ResponseEntity.ok(danhSachKhachHang);
    }
    @GetMapping("/spct")
    public String getAllSPCT(@RequestParam(required = false) Integer idKH, Model model) {
        List<SanPhamChiTietDTO> listSPCTDto = spctService.getAllSPCT();
        model.addAttribute("spctDto", listSPCTDto);
        return "BanHangTaiQuay/BanHangTaiQuay";
    }

    @GetMapping("/thong-tin-kh/{idKH}")
    @ResponseBody
    public UserDTO getKhachHangById(@PathVariable("idKH") Integer idKH) {
        return khachhangService.getKHById(idKH);
    }

    @GetMapping("/spct/{id}")
    @ResponseBody
    public SanPhamChiTietDTO getSPCTById(@PathVariable("id") Integer id) {
        return spctService.getByID(id);
    }

    @GetMapping("/tk/{id}")
    @ResponseBody
    public TaiKhoan getTaiKhoan(@PathVariable("id") Integer id) {
        return tkService.getTKByUser(id);
    }

    @GetMapping("/hd/{id}")
    @ResponseBody
    public HoaDon getHDById(@PathVariable("id") Integer id) {
        return hoaDonService.hoaDonById(id);
    }
    // Đảm bảo trả về JSON hợp lệ

    @GetMapping("/voucher")
    @ResponseBody
    public List<PhieuGiamGia> getVoucherByGiaTriHD(@RequestParam Integer tongTien) {
        return voucherService.getVoucherByGiaTriDonHang(tongTien);
    }

    @GetMapping("/voucher/{idVC}")
    @ResponseBody
    public PhieuGiamGia getVoucherById(@PathVariable("idVC") Integer idVC) {
        return voucherService.findByID(idVC);
    }
    @PostMapping("/voucher-detail/add")
    @ResponseBody
    public PhieuGiamGiaChiTiet getVoucherByGiaTriHD(@RequestBody PhieuGiamGiaChiTiet voucherDetail) {
        return voucherDetailService.save(voucherDetail);
    }
    @PostMapping("/tao-hoa-don")
    @ResponseBody
    public HoaDon createBill(@RequestBody HoaDon hoaDon) {
        return hoaDonService.createHoaDon(hoaDon);
    }

    @PutMapping("/update-hoa-don/{idHD}")
    @ResponseBody
    public HoaDon updateBill(@PathVariable("idHD") Integer idHD,
                             @RequestBody HoaDon hoaDon) {
        try {
            return hoaDonService.updateHoaDon(idHD, hoaDon);
        } catch (Exception e) {
            // Xử lý lỗi (có thể log lỗi và trả về thông báo lỗi)
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Cập nhật hóa đơn không thành công", e);
        }
    }

    @PostMapping("/tao-hoa-don-chi-tiet")
    @ResponseBody
    public ResponseEntity<Void> createBillDetails(@RequestBody List<HoaDonChiTiet> hoaDonChiTietList) {
        hdctService.createBillDetails(hoaDonChiTietList);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/tao-tai-khoan")
    @ResponseBody
    public TaiKhoan createTaiKhoan(@RequestBody TaiKhoan taiKhoan) {
        return tkService.createTK(taiKhoan);
    }
    @GetMapping("/diaChiKH")
    @ResponseBody
    public List<Address> listDiaChi(@RequestParam("idKH") Integer idKH){
        return  addressRepo.findByKhachHang_Id(idKH);
    }
    @PostMapping("/add-dia-chi")
    @ResponseBody
    public Address diaChiCreate(@RequestBody Address address){
        return addressRepo.save(address);
    }
    @GetMapping("/kiem-tra-email")
    @ResponseBody
    public ResponseEntity<Map<String, Boolean>> checkEmailExists(@RequestParam String email) {
        boolean exists = khRepo.existsByEmail(email);
        Map<String, Boolean> response = new HashMap<>();
        response.put("exists", exists);
        return ResponseEntity.ok(response);
    }
    @GetMapping("/kiem-tra-phone")
    @ResponseBody
    public ResponseEntity<Map<String, Boolean>> checkPhoneExists(@RequestParam String phone) {
        boolean exists = khRepo.existsByPhoneNumber(phone);
        Map<String, Boolean> response = new HashMap<>();
        response.put("exists", exists);
        return ResponseEntity.ok(response);
    }

}
