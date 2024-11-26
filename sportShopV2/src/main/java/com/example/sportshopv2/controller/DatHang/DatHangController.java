package com.example.sportshopv2.controller.DatHang;

import com.example.sportshopv2.dto.SanPhamChiTietDTO;
import com.example.sportshopv2.model.*;
import com.example.sportshopv2.repository.*;
import com.example.sportshopv2.service.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.example.sportshopv2.config.VNPAYService;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.util.*;
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
    private Map<Integer, String> tongTienGioHang;
    @Autowired
    private VNPAYService vnPayService;
    @Autowired
    private TaiKhoanRepo taiKhoanRepo;

    @RequestMapping("/trang-chu")
    public String trangChu(Model model) {
        List<SanPhamChiTietDTO> AllProductDetail = sanPhamChiTietService.getAllDISTINCTSPCT();
//        List<SanPhamChiTietDTO> AllProductDetail = AllProduct.stream()
//                .map(sp -> sanPhamChiTietService.findAllSPCTByIdSP(sp.getId())) // Lấy id từ SanPhamChiTiet
//                .flatMap(List::stream) // Gộp danh sách ảnh từ từng sản phẩm thành một danh sách duy nhất
//                .collect(Collectors.toList());
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
//        model.addAttribute("listsp", AllProduct);
        model.addAttribute("listspct", AllProductDetail);
        model.addAttribute("listImage", anhSanPhams);
        return "MuaHang/TrangChu";
    }

    @RequestMapping("/gio-hang")
    public String gioHang(Model model,
                          @RequestParam("id") Integer id,
                          @RequestParam("idcolor") Integer idcolor,
                          @RequestParam("idsize") Integer idsize) {
        // Lấy chi tiết sản phẩm từ service dựa trên id, idsize và idcolor
        SanPhamChiTietDTO productDetail = sanPhamChiTietService.getSPCTByIDSPIDSIZEIDCOLOR(id, idsize, idcolor);

        // Kiểm tra tài khoản người dùng (tạm thời giả sử ID=1)
        TaiKhoan tk = new TaiKhoan();
        tk.setId(1);

        // Kiểm tra giỏ hàng
        GioHang gioHang = gioHangRepo.findByIdTaiKhoan_Id(tk.getId());
        if (gioHang == null) {
            gioHang = new GioHang();
            gioHang.setIdTaiKhoan(tk);
            gioHang.setCreateAt(LocalDateTime.now());
            gioHangRepo.save(gioHang);
        }

        // Kiểm tra sản phẩm đã có trong giỏ hàng chưa
        GioHangChiTiet existingCartItem = gioHangChiTietRepo.findByGioHang_IdAndSanPhamChiTiet_Id(gioHang.getId(), productDetail.getId());
        if (existingCartItem != null) {
            existingCartItem.setSoLuong(existingCartItem.getSoLuong() + 1);
            gioHangChiTietRepo.save(existingCartItem);
        } else {
            GioHangChiTiet gioHangChiTiet = new GioHangChiTiet();
            SPCT spct = new SPCT();
            spct.setId(productDetail.getId());
            gioHangChiTiet.setSanPhamChiTiet(spct);
            gioHangChiTiet.setCreateAt(LocalDateTime.now());
            gioHangChiTiet.setGiaTien(productDetail.getGia());
            gioHangChiTiet.setGioHang(gioHang);
            gioHangChiTiet.setTrangThai("Active");
            gioHangChiTiet.setSoLuong(1);
            gioHangChiTietRepo.save(gioHangChiTiet);
        }

        // Lấy danh sách sản phẩm trong giỏ hàng
        List<GioHangChiTiet> allCart = gioHangChiTietRepo.findAllByGioHang_Id(gioHang.getId());
        model.addAttribute("danhSachGioHang", allCart);

        return "redirect:/mua-sam-SportShopV2/trang-chu";
    }


    @RequestMapping("/gio-hang-khach-hang")
    public String gioHang(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        model.addAttribute("thongTinKhachHang",  taiKhoanRepo.findTaiKhoanByUsername(username));
        List<GioHangChiTiet> listCart = gioHangChiTietRepo.findAll();

// Use a merge function to handle duplicate keys
        Map<Integer, String> tongTienHoaDon = listCart.stream().collect(Collectors.toMap(
                gioHangChiTiet -> gioHangChiTiet.getGioHang().getId(), // Key is the GioHang ID
                ghct -> {
                    // Tính tổng tiền của hóa đơn từ GioHangChiTiet
                    BigDecimal total = gioHangChiTietRepo.findAllByGioHang_Id(ghct.getGioHang().getId()).stream()
                            .map(hdct -> new BigDecimal(hdct.getGiaTien()).multiply(BigDecimal.valueOf(hdct.getSoLuong()))) // Calculate price for each item
                            .reduce(BigDecimal.ZERO, BigDecimal::add); // Sum up all the prices

                    // Làm tròn tổng tiền đến 2 chữ số thập phân
                    total = total.setScale(2, RoundingMode.HALF_UP); // Round to 2 decimal places

                    // Định dạng tổng tiền
                    return NumberFormat.getCurrencyInstance(new Locale("vi", "VN")).format(total); // Format total as currency in VND
                },
                (existingValue, newValue) -> existingValue // Handle duplicates by keeping the existing value
        ));

// Print the size of the cart for debugging
        System.out.println("ListCart size: " + listCart.size());

// Retrieve images for each product (SanPhamChiTiet) in the cart
        List<AnhSanPham> anhSanPhams = listCart.stream()
                .map(gioHangChiTiet -> anhSanPhamRepository.findByIdSPCT(gioHangChiTiet.getSanPhamChiTiet().getId())) // Lấy id từ SanPhamChiTiet
                .flatMap(List::stream) // Merge image lists for each product into one list
                .collect(Collectors.toList());

// Add the cart and images to the model for rendering the view
        model.addAttribute("listCart", listCart);
        model.addAttribute("listImage", anhSanPhams);
        model.addAttribute("tongTienGioHang", tongTienGioHang);

        return "MuaHang/GioHang";

    }

    @RequestMapping("/mua-ngay")
    public String muaNgay(Model model, @RequestParam("id") Integer id) {
        SanPham listSP = sanPhamService.findAllSanPhamById(id);
        SanPhamChiTietDTO listProductDetail = sanPhamChiTietService.getByID(id);
        List<AnhSanPham> anhSanPhams = listProductDetail.getAnhSanPham();
        List<SanPhamChiTietDTO> listSizeAndColor = sanPhamChiTietService
                .findAllSPCTByIdSP(listProductDetail.getSanPham().getId());
        model.addAttribute("listSizeAndColor", listSizeAndColor);
        model.addAttribute("listProduct", listProductDetail);
        model.addAttribute("listImage", anhSanPhams);
        return "MuaHang/mua-ngay";
    }


    @RequestMapping("/get-product-price")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> getProductPrice(@RequestParam("id") Integer id,
                                                               @RequestParam("idcolor") Integer idcolor,
                                                               @RequestParam("idsize") Integer idsize) {
        SanPhamChiTietDTO productDetail = sanPhamChiTietService.getSPCTByIDSPIDSIZEIDCOLOR(id, idsize, idcolor);

        Map<String, Object> response = new HashMap<>();
        response.put("gia", productDetail.getGia());

        return ResponseEntity.ok(response);
    }

//    @PostMapping("/VNPAY/submitOrder")
//    public String submidOrder(@RequestParam("amount") int orderTotal,
//                              @RequestParam("orderInfo") String orderInfo,
//                              HttpServletRequest request) {
//        String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
//        String vnpayUrl = vnPayService.createOrder(request, orderTotal, orderInfo, baseUrl);
//        return "redirect:" + vnpayUrl;
//    }
}

