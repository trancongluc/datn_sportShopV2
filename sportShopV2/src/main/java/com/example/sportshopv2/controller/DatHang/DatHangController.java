package com.example.sportshopv2.controller.DatHang;

import com.example.sportshopv2.dto.SanPhamChiTietDTO;
import com.example.sportshopv2.model.*;
import com.example.sportshopv2.repository.*;
import com.example.sportshopv2.service.*;
import com.example.sportshopv2.service.impl.HoaDonServiceImp;
import com.example.sportshopv2.service.impl.PhieuGiamGiaServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.example.sportshopv2.config.VNPAYService;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
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
    @Autowired
    private HoaDonRepo hoaDonRepo;
    @Autowired
    private HoaDonChiTietRepo hoaDonChiTietRepo;
    @Autowired
    private SanPhamChiTietRepository sanPhamChiTietRepository;
    @Autowired
    private SPCTRePo sanPhamChiTietRepo;
    @Autowired
    private PhieuGiamGiaKhachHangRepository phieuGiamGiaKhachHangRepository;
    @Autowired
    private PhieuGiamGiaServiceImpl phieuGiamGiaService;

    @Autowired
    private HoaDonServiceImp hoaDonServiceImp;
    @Autowired
    private AnhService anhService;
    private Integer idTK = null;
    private List<Long> dsSPCT = null;
    private Integer idVC = null;

    @Autowired
    private ChatService chatService;
    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    @Autowired
    private ChatBoxRepository chatBoxRepository;
    @Autowired
    private MessageRepository messageRepository;

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

        // Lấy accountId từ UserService
        int accountId = chatService.getAccountIdFromUsername(username);
        int getName = chatService.getNameFromIDUser(username);
        Optional<NguoiDung> name = chatService.getName(getName);

        model.addAttribute("accountId", accountId);
        // Lấy danh sách tất cả chatboxes
// Kiểm tra xem accountId đã có chatBox hay chưa
        List<message> message = chatService.getMesByAccountId(accountId);

        if (message.isEmpty()) {
            // Nếu không tìm thấy ChatBox, tạo mới ChatBox với tên đặt theo tên người dùng
            chatBox newChatBox = new chatBox();
            newChatBox.setName(name.get().getFull_name()); // Đặt tên ChatBox theo tên người dùng
            newChatBox.setCreateAt(LocalDateTime.now());
            newChatBox.setCreateBy(accountId);
            // Lưu ChatBox mới vào cơ sở dữ liệu
            chatService.saveChatBox(newChatBox);
        }
        /*chatBox cb = chatService.findChatBoxByAccountId(accountId);
        // Lấy danh sách tin nhắn của ChatBox
        List<message> messages = chatService.getMessagesByChatBoxId(cb.getId());

        // Thêm ChatBox và tin nhắn vào Model để gửi ra view
        model.addAttribute("chatBox", cb.getId());
        model.addAttribute("messages", messages);*/
        model.addAttribute("accountId", accountId);
        model.addAttribute("imageUrls", imageUrls);
        model.addAttribute("listspct", AllProductDetail);
        model.addAttribute("listImage", anhSanPhams);

        return "MuaHang/TrangChu";
    }

    @RequestMapping("/gio-hang")
    public String gioHang(Model model,
                          @RequestParam("id") Integer id,
                          @RequestParam("idcolor") Integer idcolor,
                          @RequestParam("idsize") Integer idsize, @RequestParam("soLuong") Integer soLuong) {
        Integer soLuongDat = Integer.valueOf(soLuong);
        // Lấy chi tiết sản phẩm từ service dựa trên id, idsize và idcolor
        SanPhamChiTietDTO productDetail = sanPhamChiTietService.getSPCTByIDSPIDSIZEIDCOLOR(id, idsize, idcolor);

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
            existingCartItem.setSoLuong(existingCartItem.getSoLuong() + soLuongDat);
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
            gioHangChiTiet.setSoLuong(soLuongDat);
            gioHangChiTietRepo.save(gioHangChiTiet);
        }

        // Lấy danh sách sản phẩm trong giỏ hàng
        List<GioHangChiTiet> allCart = gioHangChiTietRepo.findAllByGioHang_Id(gioHang.getId());
        model.addAttribute("danhSachGioHang", allCart);

        return "redirect:/mua-sam-SportShopV2/trang-chu";
    }


    @RequestMapping("/gio-hang-khach-hang")
    public String gioHang(Model model, @RequestParam("id") Integer id,
                          @RequestParam(value = "selectedProducts", required = false) List<Long> selectedProductIds, @RequestParam(value = "idVoucher", defaultValue = "0") Integer idVoucher) {
        TaiKhoan taiKhoan = taiKhoanRepo.findTaiKhoanById(id);
//        List<PhieuGiamGiaKhachHang> voucher = phieuGiamGiaKhachHangRepository.findAllByIdTaiKhoan_IdAndDeleted(id, false);
//        if (idVoucher != 0) {
//            model.addAttribute("giaTriGiam", phieuGiamGiaKhachHangRepository.findByIdPhieuGiamGia_Id(id));
//        }
        idTK = id;
        model.addAttribute("thongTinKhachHang", taiKhoan);
        model.addAttribute("selectedProductIds", selectedProductIds != null ? selectedProductIds : Collections.emptyList());
        dsSPCT = selectedProductIds;
        List<GioHangChiTiet> listCart = gioHangChiTietRepo.findAllByGioHang_IdTaiKhoan_Id(id);
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setDecimalSeparator(',');
        symbols.setGroupingSeparator('.');

        DecimalFormat formatter = new DecimalFormat("#,###", symbols);

        // Tạo một Map để chứa giá trị đã định dạng cho từng sản phẩm
        Map<Integer, String> formattedTotals = new HashMap<>();
        for (GioHangChiTiet product : listCart) {
            Float productTotal = product.getGiaTien() * product.getSoLuong();
            String formattedTotal = formatter.format(productTotal); // Định dạng số tiền
            formattedTotals.put(product.getId(), formattedTotal); // Lưu vào Map
            System.out.println("Formatted Totals: " + formattedTotals);
        }
        model.addAttribute("formattedTotals", formattedTotals);
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
//        model.addAttribute("Voucher", voucher);
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

    @PostMapping("/update-quantity")
    public ResponseEntity<?> updateQuantity(@RequestBody Map<String, Object> payload, RedirectAttributes redirectAttributes) {
        try {
            Integer soLuong = Integer.parseInt(payload.get("soLuong").toString());
            if (payload.get("soLuong") != null || payload.get("idSPCT") != null) {
                SanPhamChiTietDTO sanPham = sanPhamChiTietService.getByID(Integer.valueOf(payload.get("idSPCT").toString()));
                if (sanPham.getSoLuong() < soLuong) {
//                    redirectAttributes.addFlashAttribute("message", "Số lượng không hợp lệ!!!");
                    return ResponseEntity.status(HttpStatus.NOT_FOUND)
                            .body(Map.of("error", "Số lượng không hợp lệ!!!"));
                }
            }
            // Ensure proper data types
            if (payload.get("id") == null || payload.get("soLuong") == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(Map.of("error", "Missing required fields: id or soLuong"));
            }

            Integer id = Integer.parseInt(payload.get("id").toString());  // Convert to Integer
            // Convert to Integer

            Optional<GioHangChiTiet> product = gioHangChiTietRepo.findById(id);
            if (product.isPresent()) {
                GioHangChiTiet existingProduct = product.get();
                existingProduct.setSoLuong(soLuong);
                gioHangChiTietRepo.save(existingProduct);
                return ResponseEntity.ok(Map.of("message", "Số lượng đã được cập nhật."));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("error", "Sản phẩm không tồn tại."));
            }
        } catch (NumberFormatException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "Invalid data format for id or soLuong."));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "An unexpected error occurred."));
        }
    }

    @PostMapping("/product-quantity")
    public ResponseEntity<?> productQuantity(@RequestBody Map<String, Object> payload, RedirectAttributes redirectAttributes) {
        try {
            // Kiểm tra giá trị đầu vào
            if (payload.get("id") == null || payload.get("soLuong") == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(Map.of("error", "Missing required fields: id or soLuong"));
            }

            Integer soLuong = Integer.parseInt(payload.get("soLuong").toString());
            Integer idSPCT = Integer.parseInt(payload.get("idSPCT").toString());

            SanPhamChiTietDTO sanPham = sanPhamChiTietService.getByID(idSPCT);

            // Kiểm tra số lượng sản phẩm
            if (sanPham.getSoLuong() < soLuong) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("error", "Số lượng không hợp lệ!!!"));
            }

            // Xử lý logic nếu dữ liệu hợp lệ
            return ResponseEntity.ok(Map.of(
                    "message", "Số lượng hợp lệ",
                    "idSPCT", idSPCT,
                    "soLuong", soLuong
            ));
        } catch (NumberFormatException e) {
            // Trường hợp lỗi format
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "Invalid data format for id or soLuong."));
        } catch (Exception e) {
            // Bắt lỗi chung
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "An unexpected error occurred."));
        }
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

    @GetMapping("/submitOrder")
    public String submitOrder(@RequestParam("pay-status") String paystatus,
                              @RequestParam("amount") String orderTotal,
                              @RequestParam(value = "orderInfo", defaultValue = "0") String orderInfo,
                              @RequestParam("tinh_name") String tinh,
                              @RequestParam("phuong_name") String phuong,
                              @RequestParam("quan_name") String quan,
                              @RequestParam(value = "email", required = false) String email,
                              @RequestParam("nguoiDung.phone_number") String sdt,
                              @RequestParam(value = "nguoiDung.full_name", required = false) String hoTen,
                              @RequestParam("soNha") String soNha,
                              @RequestParam("selectedProducts") List<Long> selectedProducts,
                              @RequestParam(value = "moneyShip", defaultValue = "0.0") String moneyShip,
                              @RequestParam(value = "voucher", defaultValue = "0.0") String moneyVoucher,
                              @RequestParam(value = "soLuongSanPham") Integer soLuong,
                              HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
        if (hoTen == null || hoTen.isEmpty()) {
            hoTen = "Unknown Customer";  // Set a default value if not provided
        }
        if (paystatus == null || paystatus.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "Phương thức thanh toán không được để trống!");
            return "redirect:/mua-sam-SportShopV2/gio-hang-khach-hang?id=" + idTK;
        }
        System.out.println(orderTotal);
        if (orderTotal == null || orderTotal.equals("0VND")) {
            redirectAttributes.addFlashAttribute("message", "Vui lòng chọn sản phẩm để có thể mua hàng");
            return "redirect:/mua-sam-SportShopV2/gio-hang-khach-hang?id=" + idTK;
        }
        Float ship = 0.0f;
        Float voucher = 0.0f;
        TaiKhoan tk = new TaiKhoan();
        tk.setId(2);  // Assuming you have logic to set this properly
        TaiKhoan taiKhoan = taiKhoanRepo.findTaiKhoanById(idTK);  // Make sure 'idTK' is initialized or passed correctly

        HoaDon hoaDon = new HoaDon();
        String invoiceCode = "HD-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        hoaDon.setBillCode(invoiceCode);  // Use default if orderInfo is null
        hoaDon.setStatus("Chờ xác nhận");
        hoaDon.setId_staff(tk);
        hoaDon.setUser_name(taiKhoan.getUsername());
        hoaDon.setId_account(taiKhoan);
        hoaDon.setPhone_number(sdt);
        hoaDon.setType("Chuyển phát");
        hoaDon.setUser_name(hoTen);
        hoaDon.setNote(orderInfo);
        hoaDon.setEmail(email);
        hoaDon.setCreateAt(LocalDateTime.now());
        hoaDon.setCreate_by(taiKhoan.getUsername());  // Assuming the logged-in user is set correctly
        hoaDon.setUpdateAt(LocalDateTime.now());
        hoaDon.setPay_method("Chuyển khoản");
        hoaDon.setPay_status(paystatus);
        ship = Float.valueOf(moneyShip.replaceAll("[^\\d]", ""));
        voucher = Float.valueOf(moneyVoucher.replaceAll("[^\\d]", ""));
        hoaDon.setMoney_ship(ship);
        hoaDon.setMoney_reduced(voucher);
        hoaDon.setNote(orderInfo);
        // Sanitize the orderTotal (remove non-numeric characters)
        String orderTotalStr = orderTotal.replaceAll("[^\\d]", "");
        hoaDon.setTotal_money(orderTotalStr.isEmpty() ? 0 : Float.valueOf(orderTotalStr));  // Ensure no empty string

        // Set the address
        hoaDon.setAddress(soNha + " " + phuong + " " + quan + " " + tinh);  // Add space between components

        hoaDonRepo.save(hoaDon);
        PhieuGiamGia phieuGiamGia = phieuGiamGiaService.findByID(idVC);
        if (phieuGiamGia.getQuantity() >= 1) {
            phieuGiamGia.setQuantity(phieuGiamGia.getQuantity() - 1);
            phieuGiamGiaService.save(phieuGiamGia);
            if (phieuGiamGia.getQuantity() == 0) {
                PhieuGiamGia hetPhieuGiamGia = phieuGiamGiaService.findByID(idVC);
                hetPhieuGiamGia.setDeleted(true);
                hetPhieuGiamGia.setStatus("Không hoạt động");
                phieuGiamGiaService.save(hetPhieuGiamGia);
            }
        }
        // Assuming you have a list of product details (dsSPCT) to create bill details
        List<SPCT> spctList = sanPhamChiTietRepo.findByIdIn(selectedProducts);  // Ensure dsSPCT is populated
        List<GioHangChiTiet> gioHangChiTiets = gioHangChiTietRepo.findAllBySanPhamChiTiet_IdIn(selectedProducts);

        // Create HoaDonChiTiet for each SanPhamChiTiet and associate it with the HoaDon
        for (SPCT sanPhamChiTiet : spctList) {
            HoaDonChiTiet hoaDonChiTiet = new HoaDonChiTiet();
            GioHang gioHang = gioHangRepo.findByIdTaiKhoan_Id(idTK);
            if (gioHang == null) {
                throw new IllegalArgumentException("Giỏ hàng không tồn tại hoặc chưa được tạo cho tài khoản ID: " + idTK);
            }

            if (sanPhamChiTiet == null) {
                throw new IllegalArgumentException("Sản phẩm chi tiết không tồn tại hoặc không hợp lệ: " + sanPhamChiTiet.getId());
            }
            GioHangChiTiet gioHangChiTiet = gioHangChiTietRepo.findByGioHang_IdAndSanPhamChiTiet_Id(gioHang.getId(), sanPhamChiTiet.getId());
            if (gioHangChiTiet == null) {
                throw new IllegalArgumentException("Không tìm thấy chi tiết giỏ hàng cho sản phẩm ID: " + gioHangChiTiet.getId());
            }
            System.out.println(gioHangChiTiet.getId());
            hoaDonChiTiet.setSanPhamChiTiet(sanPhamChiTiet);  // Set the product detail
            hoaDonChiTiet.setHoaDon(hoaDon);  // Associate the bill with the bill detail
            hoaDonChiTiet.setQuantity(gioHangChiTiet.getSoLuong());
            hoaDonChiTiet.setPrice(Float.valueOf(orderTotalStr));
            hoaDonChiTiet.setCreate_at(LocalDateTime.now());
            hoaDonChiTietRepo.save(hoaDonChiTiet);  // Save the bill detail
        }
        for (GioHangChiTiet gioHangChiTiet : gioHangChiTiets) {
            SanPhamChiTiet sanPhamChiTiet = sanPhamChiTietService.findSPCTById(gioHangChiTiet.getSanPhamChiTiet().getId());
            sanPhamChiTiet.setSoLuong(sanPhamChiTiet.getSoLuong() - gioHangChiTiet.getSoLuong());
            sanPhamChiTietService.updateSoLuongSanPhamChiTiet(sanPhamChiTiet.getId(), sanPhamChiTiet);
            gioHangChiTietRepo.delete(gioHangChiTiet);
        }
        if (paystatus.equals("Thanh toán khi nhận hàng")) {
            redirectAttributes.addFlashAttribute("message", "Đặt hàng thành công!");
            return "redirect:/mua-sam-SportShopV2/gio-hang-khach-hang?id=" + idTK;
        } else {
            // Generate VNPay URL
            String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
            String vnpayUrl = vnPayService.createOrder(request, orderTotalStr, orderInfo, baseUrl);
            return "redirect:" + vnpayUrl;
        }
    }

    @GetMapping("/voucher/details/{id}")
    @ResponseBody
    public PhieuGiamGia getVoucherDetails(@PathVariable Integer id) {
        PhieuGiamGia voucher = phieuGiamGiaService.findByID(id);
        idVC = id;
        if (voucher == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Voucher không tồn tại");
        }
        return voucher; // Trả về JSON
    }

    @DeleteMapping("/xoa-san-pham-gio-hang/{id}")
    @ResponseBody
    public GioHangChiTiet deleteProductCart(@PathVariable Integer id) {

        GioHangChiTiet productInCart = gioHangChiTietRepo.findById(id).orElse(null);

        if (productInCart == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Sản phẩm không tồn tại trong giỏ hàng");
        }

        gioHangChiTietRepo.delete(productInCart);
        return productInCart;
    }

    @PostMapping("/get-voucher")
    @ResponseBody
    public ResponseEntity<List<PhieuGiamGia>> getVoucher(@RequestBody Map<String, String> request) {
        String total = request.get("total");
        System.out.println(total);
        Integer vc = Integer.valueOf(total.replaceAll("[^\\d]", ""));
        List<PhieuGiamGia> vouchers = phieuGiamGiaService.getVoucherByGiaTriDonHang(vc);

        System.out.println("Vouchers sent to client: " + vouchers); // Log dữ liệu gửi
        return ResponseEntity.ok(vouchers);
    }

    @GetMapping("/tra-cuu-don-hang")
    public String traCuuDonHang() {

        return "MuaHang/TraCuuDonHang"; // Trả về JSON
    }

    @GetMapping("/theo-doi-hoa-don")
    public String getHoaDonDetail(@RequestParam("tenHoaDon") String tenHoaDon, Model model) {

        HoaDon hoaDon = hoaDonServiceImp.getBillDetailByBillCode(tenHoaDon);
        List<HoaDonChiTiet> listSPCT = hoaDon.getBillDetails();
        for (HoaDonChiTiet hdct : listSPCT) {
            AnhSanPham anhSanPham = anhService.anhSanPhamByIDSPCT(hdct.getSanPhamChiTiet().getId());
            model.addAttribute("anhSP", anhSanPham);
        }
        if (hoaDon == null) {
            // Xử lý trường hợp không tìm thấy hóa đơn
            model.addAttribute("error", "Không tìm thấy hóa đơn với mã: " + tenHoaDon);
            return "redirect:/doi-tra/view";
        }
        model.addAttribute("hoaDon", hoaDon);

        return "MuaHang/TheoDoiHoaDon";
    }

    //Chat Functions

    @MessageMapping("client/sendMessage")
    @SendTo("client/topic/messages")
    public message sendMessage(@Payload message message) {
        //Log test tin nhắn nhận được phía Client
        System.out.println("Received message: " + message);
        // Lưu tin nhắn vào cơ sở dữ liệu hoặc xử lý thêm
        return message; // Trả về tin nhắn để gửi lại cho tất cả người subscribe
    }

    @PostMapping("/sendMessage")
    public ResponseEntity<message> sendMessage(@RequestParam("chatBoxId") int chatBoxId,
                                               @RequestParam("accountId") int accountId,
                                               @RequestParam("content") String content) {
        // Lưu tin nhắn vào cơ sở dữ liệu
        message savedMessage = chatService.saveMessage(chatBoxId, accountId, "client", content);

        // Gửi tin nhắn đến các subscriber thông qua STOMP
        messagingTemplate.convertAndSend("/topic/messages", savedMessage);

        // Trả về thông tin tin nhắn đã lưu
        return ResponseEntity.ok(savedMessage);
    }

    // API để lấy danh sách tin nhắn theo chatbox
    @GetMapping("/messages/{chatBoxId}")
    @ResponseBody
    public List<message> getMessagesByChatBoxId(@PathVariable int chatBoxId) {
        return chatService.getMessagesByChatBoxId(chatBoxId);
    }
}
