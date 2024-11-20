package com.example.sportshopv2.controller.DatHang;

import com.example.sportshopv2.dto.SanPhamChiTietDTO;
import com.example.sportshopv2.model.*;
import com.example.sportshopv2.repository.AnhSanPhamRepository;
import com.example.sportshopv2.repository.GioHangChiTietRepo;
import com.example.sportshopv2.repository.GioHangRepo;
import com.example.sportshopv2.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/mua-sam-SportShopV2")
public class DatHangController {
    private ChatLieuService chatLieuService;
    private CoGiayService coGiayService;
    private DeGiayService deGiayService;
    @Autowired
    private KichThuocService kichThuocService;
    @Autowired
    private MauSacService mauSacService;
    @Autowired
    private SanPhamService sanPhamService;
    private TheLoaiService theLoaiService;
    private ThuongHieuService thuongHieuService;
    @Autowired
    private SanPhamChiTietService sanPhamChiTietService;
    @Autowired
    private AnhSanPhamRepository anhSanPhamRepository;
    @Autowired
    private GioHangRepo gioHangRepo;
    @Autowired
    private GioHangChiTietRepo gioHangChiTietRepo;

    @RequestMapping("/trang-chu")
    public String trangChu(Model model) {
        List<SanPham> AllProduct = sanPhamService.findAllSanPham();
        List<SanPhamChiTietDTO> AllProductDetail = AllProduct.stream()
                .map(sp -> sanPhamChiTietService.findAllSPCTByIdSP(sp.getId())) // Lấy id từ SanPhamChiTiet
                .flatMap(List::stream) // Gộp danh sách ảnh từ từng sản phẩm thành một danh sách duy nhất
                .collect(Collectors.toList());
        List<AnhSanPham> anhSanPhams = AllProductDetail.stream()
                .map(spct -> anhSanPhamRepository.findByIdSPCT(spct.getId())) // Lấy id từ SanPhamChiTiet

                .flatMap(List::stream) // Gộp danh sách ảnh từ từng sản phẩm thành một danh sách duy nhất
                .collect(Collectors.toList());

        if (anhSanPhams.isEmpty()) {
            System.out.println("Danh sách hình ảnh rỗng.");
        }
        List<String> imageUrls = anhSanPhams.stream()
                .map(anh -> anh.getTenAnh() != null ? "/images/" + anh.getTenAnh() : "/images/giayMau.png")
                .collect(Collectors.toList());

        model.addAttribute("imageUrls", imageUrls);
        model.addAttribute("listsp", AllProduct);
        model.addAttribute("listspct", AllProductDetail);
        model.addAttribute("listImage", anhSanPhams);
        return "MuaHang/TrangChu";
    }

    @RequestMapping("/gio-hang")
    public String gioHang(Model model, @RequestParam("id") Integer id) {
        SanPhamChiTietDTO productDetail = sanPhamChiTietService.getByID(id);
        TaiKhoan tk = new TaiKhoan();
        tk.setId(1);

        GioHang gioHang = new GioHang();
        gioHang.setIdTaiKhoan(tk);
        gioHang.setCreateAt(LocalDateTime.now());
        gioHangRepo.save(gioHang);

        GioHangChiTiet gioHangChiTiet = new GioHangChiTiet();
        SPCT spct = new SPCT();
        spct.setId(productDetail.getId());
        gioHangChiTiet.setIdSanPhamChiTiet(spct);
        gioHangChiTiet.setCreateAt(LocalDateTime.now());
        gioHangChiTiet.setGiaTien(productDetail.getGia());
        gioHangChiTiet.setIdGioHang(gioHang);
        gioHangChiTiet.setTrangThai("Active");
        gioHangChiTiet.setSoLuong(1);
        gioHangChiTietRepo.save(gioHangChiTiet);
        List<GioHangChiTiet> AllCart = gioHangChiTietRepo.findAll();
        model.addAttribute("danhSachGioHang", AllCart);
//        List<AnhSanPham> anhSanPhams = AllProductDetail.stream()
//                .map(spct -> anhSanPhamRepository.findByIdSPCT(spct.getId())) // Lấy id từ SanPhamChiTiet
//                .flatMap(List::stream) // Gộp danh sách ảnh từ từng sản phẩm thành một danh sách duy nhất
//                .collect(Collectors.toList());
//
//        if (anhSanPhams.isEmpty()) {
//            System.out.println("Danh sách hình ảnh rỗng.");
//        }
//        model.addAttribute("spct", AllProductDetail);
//        model.addAttribute("listImage", anhSanPhams);
        return "MuaHang/GioHang";
    }

    @RequestMapping("/gio-hang-khach-hang")
    public String gioHang(Model model) {
        List<GioHangChiTiet> listCart = gioHangChiTietRepo.findAll();
        List<AnhSanPham> anhSanPhams = listCart.stream()
                .map(gioHangChiTiet -> anhSanPhamRepository.findByIdSPCT(gioHangChiTiet.getIdSanPhamChiTiet().getId())) // Lấy id từ SanPhamChiTiet
                .flatMap(List::stream) // Gộp danh sách ảnh từ từng sản phẩm thành một danh sách duy nhất
                .collect(Collectors.toList());
        model.addAttribute("listCart", listCart);
        model.addAttribute("listImage", anhSanPhams);
        return "MuaHang/GioHang";
    }

    @RequestMapping("/mua-ngay")
    public String muaNgay(Model model, @RequestParam("id") Integer id) {
        SanPham listSP =  sanPhamService.findAllSanPhamById(id);
        SanPhamChiTietDTO listProductDetail = sanPhamChiTietService.getByID(id);
        List<AnhSanPham> anhSanPhams = listProductDetail.getAnhSanPham();
        List<SanPhamChiTietDTO> listSizeAndColor = sanPhamChiTietService
                .findAllSPCTByIdSP(listProductDetail.getSanPham().getId());
        model.addAttribute("listSizeAndColor", listSizeAndColor);
        model.addAttribute("listProduct", listProductDetail);
        model.addAttribute("listImage", anhSanPhams);
        return "MuaHang/mua-ngay";
    }
}
