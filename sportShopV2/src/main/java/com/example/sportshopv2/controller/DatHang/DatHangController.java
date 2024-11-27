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
import org.springframework.web.bind.annotation.*;
import com.example.sportshopv2.config.VNPAYService;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    @Autowired
    private AddressRepo addressRepo;
    private Integer idTK = null;

    @RequestMapping("/trang-chu")
    public String trangChu(Model model) {
        // Lấy thông tin người dùng hiện tại
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Kiểm tra nếu người dùng chưa đăng nhập
        if (authentication == null || !authentication.isAuthenticated() ||
                "anonymousUser".equals(authentication.getName())) {
            // Chuyển hướng đến trang đăng nhập
            return "redirect:/login";
        }

        // Lấy tên người dùng đã đăng nhập
        String username = authentication.getName();

        // Lấy thông tin tài khoản
        TaiKhoan taiKhoan = taiKhoanRepo.findTaiKhoanByUsername(username);
        if (taiKhoan == null) {
            taiKhoan = new TaiKhoan(); // Khởi tạo đối tượng rỗng nếu không tìm thấy
        }
        idTK = taiKhoan.getId();
        model.addAttribute("thongTinKhachHang", taiKhoan);

        // Xử lý danh sách sản phẩm
        List<SanPhamChiTietDTO> AllProductDetail = sanPhamChiTietService.getAllDISTINCTSPCT();
        List<AnhSanPham> anhSanPhams = AllProductDetail.stream()
                .map(spct -> anhSanPhamRepository.findByIdSPCT(spct.getId()))
                .flatMap(List::stream)
                .collect(Collectors.toList());

        List<String> imageUrls = anhSanPhams.stream()
                .map(anh -> anh.getTenAnh() != null ? "/images/" + anh.getTenAnh() : "/images/giayMau.png")
                .collect(Collectors.toList());

        model.addAttribute("imageUrls", imageUrls);
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
        tk.setId(idTK);

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
    public String gioHang(Model model, @RequestParam("id") Integer id,
                          @RequestParam(value = "selectedProducts", required = false) List<Long> selectedProductIds) {
        TaiKhoan taiKhoan = taiKhoanRepo.findTaiKhoanById(id);
        idTK = id;
        model.addAttribute("thongTinKhachHang", taiKhoan);
        model.addAttribute("selectedProductIds", selectedProductIds != null ? selectedProductIds : Collections.emptyList());

        List<GioHangChiTiet> listCart = gioHangChiTietRepo.findAllByGioHang_IdTaiKhoan_Id(id);
        List<Address> diaChi = addressRepo.findByKhachHang_Id(taiKhoan.getNguoiDung().getId());

        if (diaChi.size() == 0) {
            model.addAttribute("listDiaChi", Collections.EMPTY_LIST);
        } else {
            model.addAttribute("listDiaChi", diaChi);
        }

        Map<Integer, String> tongTienHoaDon = listCart.stream().collect(Collectors.toMap(
                gioHangChiTiet -> gioHangChiTiet.getGioHang().getId(),
                ghct -> {
                    BigDecimal total = gioHangChiTietRepo.findAllByGioHang_Id(ghct.getGioHang().getId()).stream()
                            .map(hdct -> new BigDecimal(hdct.getGiaTien()).multiply(BigDecimal.valueOf(hdct.getSoLuong())))
                            .reduce(BigDecimal.ZERO, BigDecimal::add);
                    total = total.setScale(2, RoundingMode.HALF_UP);
                    return NumberFormat.getCurrencyInstance(new Locale("vi", "VN")).format(total);
                },
                (existingValue, newValue) -> existingValue
        ));

        List<AnhSanPham> anhSanPhams = listCart.stream()
                .map(gioHangChiTiet -> anhSanPhamRepository.findByIdSPCT(gioHangChiTiet.getSanPhamChiTiet().getId()))
                .flatMap(List::stream)
                .collect(Collectors.toList());

        model.addAttribute("listCart", listCart);
        model.addAttribute("listImage", anhSanPhams);
        model.addAttribute("tongTienGioHang", tongTienHoaDon);
        return "MuaHang/GioHang";
    }

    @PostMapping("/dat-hang")
    public String submitOrder(@RequestParam("selectedProducts") List<Long> selectedProductIds,
                              Model model, RedirectAttributes redirectAttributes) {
        if (selectedProductIds == null || selectedProductIds.isEmpty()) {
            model.addAttribute("error", "Không có sản phẩm nào được chọn");
            return "redirect:/mua-sam-SportShopV2/gio-hang-khach-hang?id=" + idTK;
        }

        // Tính tổng tiền dựa trên danh sách sản phẩm được chọn
        List<GioHangChiTiet> selectedProducts = gioHangChiTietRepo.findAllBySanPhamChiTiet_IdInAndGioHang_IdTaiKhoan_Id(selectedProductIds, idTK);
        double totalPrice = selectedProducts.stream()
                .mapToDouble(sp -> sp.getGiaTien() * sp.getSoLuong())
                .sum();

        // Lưu thông tin hoặc chuyển đến trang tiếp theo
        redirectAttributes.addFlashAttribute("totalPrice", totalPrice);
        redirectAttributes.addFlashAttribute("selectedProductIds", selectedProductIds); // Lưu lại sản phẩm đã chọn
        return "redirect:/mua-sam-SportShopV2/gio-hang-khach-hang?id=" + idTK;
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

