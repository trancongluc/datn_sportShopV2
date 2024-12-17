package com.example.sportshopv2.controller;

import com.example.sportshopv2.dto.SanPhamChiTietDTO;
import com.example.sportshopv2.model.*;
import com.example.sportshopv2.repository.*;
import com.example.sportshopv2.service.DotGiamGiaService;
import com.example.sportshopv2.service.SanPhamChiTietService;
import com.example.sportshopv2.service.impl.DotGiamGiaServiceImp;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Controller
@RequestMapping("/dot-giam-gia")
public class DotGiamGiaController {
    @Autowired
    private SanPhamChiTietService spctService;
    @Autowired
    private DotGiamGiaRepo dotGiamGiaRepo;
    @Autowired
    private DotGiamGiaChiTietRepo productDetailPromotionRepo;
    @Autowired
    private DotGiamGiaServiceImp dotGiamGiaServiceImp;
    @Autowired
    private SanPhamRepository sanPhamRep;
    @Autowired
    private SanPhamChiTietRepository SPCTRep;
    @Autowired
    private SPCTRePo spctRePo;

    @Autowired
    private DotGiamGiaService dotGiamGiaService;

    @GetMapping("/view")
    public String DotGiamGia(Model model) {
        List<DotGiamGia> allPromotions = dotGiamGiaRepo.findAll(Sort.by(Sort.Direction.DESC, "id"));

        // Cập nhật trạng thái ngay lập tức nếu hết hạn
        for (DotGiamGia dotGiamGia : allPromotions) {
            if (dotGiamGia.getEndDate() != null && dotGiamGia.getEndDate().isBefore(LocalDateTime.now())) {
                dotGiamGia.setStatus("Hết hạn");
                dotGiamGiaRepo.save(dotGiamGia); // Cập nhật vào cơ sở dữ liệu ngay lập tức
            }
        }

        model.addAttribute("listDotGiamGia", allPromotions);
        return "DotGiamGia/dotGiamGia";
    }

    @GetMapping("/add-dot-giam-gia")
    public String showAddGiamGiaForm(Model model,
                                     @RequestParam(defaultValue = "0") int page,
                                     @RequestParam(defaultValue = "3") int size) {
        model.addAttribute("dotGiamGia", new DotGiamGia());

        Pageable pageable = PageRequest.of(page, size);
        Page<SanPham> sanPhamPage = sanPhamRep.findAll(pageable);
        model.addAttribute("sanPhamPage", sanPhamPage);

        List<SanPhamChiTietDTO> listSPCTDto = spctService.getAllSPCT();
        model.addAttribute("spctDto", listSPCTDto);

        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", sanPhamPage.getTotalPages());
        model.addAttribute("totalItems", sanPhamPage.getTotalElements());

        return "DotGiamGia/addDotGiamGia";
    }

    private Map<Long, Float> originalPricesCache = new HashMap<>();

    @PostMapping("/save")
    public String saveDotGiamGia(
            @RequestParam("selectProductID") List<Long> selectedProducts,
            @ModelAttribute DotGiamGia dotGiamGia,
            RedirectAttributes redirectAttributes) {

        if (dotGiamGia.getEndDate() != null && dotGiamGia.getEndDate().isBefore(LocalDateTime.now())) {
            dotGiamGia.setStatus("Hết hạn");
        } else {
            dotGiamGia.setStatus("Đang diễn ra");
        }

        DotGiamGia savedPromotion = dotGiamGiaRepo.save(dotGiamGia);

        System.out.println("Saved Promotion ID: " + savedPromotion.getId());


        List<DotGiamGiaChiTiet> productDetailPromotions = new ArrayList<>();

        System.out.println(selectedProducts + "ID đã chọn");
        // Tìm kiếm sản phẩm chi tiết theo ID
        List<SPCT> sanPhamChiTietList = spctRePo.findByidSanPham_IdIn(selectedProducts);
        System.out.println(sanPhamChiTietList.isEmpty() + "tắt unikey ");
        if (sanPhamChiTietList.isEmpty()) {
            System.out.println("No products found for the selected IDs.");
        } else {
            for (SPCT sanPhamChiTiet : sanPhamChiTietList) {
                DotGiamGiaChiTiet productDetailPromotion = new DotGiamGiaChiTiet();
                productDetailPromotion.setDotGiamGia(savedPromotion);
                productDetailPromotion.setSpct(sanPhamChiTiet);
                productDetailPromotion.setName(savedPromotion.getName());

                String code = generateRandomCode();
                productDetailPromotion.setCode(code);

                productDetailPromotion.setStatus(savedPromotion.getStatus());
                productDetailPromotion.setCreateAt(savedPromotion.getCreateAt());
                productDetailPromotion.setCreateBy(savedPromotion.getCreateBy());
                productDetailPromotion.setDeleted(savedPromotion.getDeleted());

                productDetailPromotions.add(productDetailPromotion);
                System.out.println("Created Product Detail Promotion ID: " + productDetailPromotion.getId());
            }
        }

        // Lưu tất cả các chi tiết sản phẩm nếu có
        if (!productDetailPromotions.isEmpty()) {
            productDetailPromotionRepo.saveAll(productDetailPromotions);
            System.out.println("Saved Product Detail Promotions: " + productDetailPromotions.size());
        } else {
            System.out.println("No product detail promotions to save.");
        }

        redirectAttributes.addFlashAttribute("successMessage", "Lưu đợt giảm giá thành công!");
        return "redirect:/dot-giam-gia/view";
    }

    private String generateRandomCode() {
        Random random = new Random();
        int randomNumber = random.nextInt(10000);
        return "pdp" + String.format("%04d", randomNumber);
    }

    @GetMapping("/detail/{id}")
    public String showDetail(@PathVariable Integer id, Model model, RedirectAttributes redirectAttributes,
                             @RequestParam(defaultValue = "0") int page,
                             @RequestParam(defaultValue = "3") int size,
                             @RequestParam(required = false) List<Integer> selectedProductIds) { // Nhận danh sách ID sản phẩm đã chọn từ request

        // Lấy danh sách sản phẩm
        model.addAttribute("listSanPham", sanPhamRep.findAll());

        // Kiểm tra ID
        if (id == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "ID không hợp lệ!");
            return "redirect:/dot-giam-gia/view";
        }

        // Tìm đợt giảm giá
        DotGiamGia dotGiamGia = dotGiamGiaServiceImp.findByID(id);
        List<DotGiamGiaChiTiet> dotGiamGiaChiTiets = productDetailPromotionRepo.findAllByDotGiamGia_IdAndStatus(dotGiamGia.getId(), "Đang diễn ra");
        if (dotGiamGia == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "Không tìm thấy đợt giảm giá!");
            return "redirect:/dot-giam-gia/view";
        }
        ArrayList<Integer> listSPCTDetails = new ArrayList<>();
        for (DotGiamGiaChiTiet dotGiamGiaChiTiet : dotGiamGiaChiTiets) {
            Optional<SPCT> spct = spctRePo.findById(dotGiamGiaChiTiet.getSpct().getId());
            System.out.println(spct.get().getIdSanPham().getId());
            listSPCTDetails.add(spct.get().getIdSanPham().getId());
        }

        // Thêm đợt giảm giá vào model
        model.addAttribute("dotGiamGia", dotGiamGia);

        // Phân trang cho danh sách sản phẩm
        Pageable pageable = PageRequest.of(page, size);
        Page<SanPham> sanPhamPage = sanPhamRep.findAll(pageable);
        model.addAttribute("listIdProduct", listSPCTDetails);
        model.addAttribute("sanPhamPage", sanPhamPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", sanPhamPage.getTotalPages());
        model.addAttribute("totalItems", sanPhamPage.getTotalElements());

        // Nếu selectedProductIds là null, khởi tạo nó là một danh sách rỗng
        if (selectedProductIds == null) {
            selectedProductIds = new ArrayList<>();
        }
        model.addAttribute("selectedProductIds", selectedProductIds);

        return "DotGiamGia/detail";
    }

    @PostMapping("/update")
    public String updateDotGiamGia(@ModelAttribute DotGiamGia dotGiamGia, RedirectAttributes redirectAttributes,
                                   @RequestParam List<Long> selectedProducts) {
        if (dotGiamGia.getId() == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "ID không hợp lệ!");
            return "redirect:/dot-giam-gia/view";
        }

        try {
            // Cập nhật đợt giảm giá
            dotGiamGiaService.update(dotGiamGia);

            // Tìm các sản phẩm chi tiết (SPCT) đã được chọn để thêm vào đợt giảm giá
            List<DotGiamGiaChiTiet> productDetailPromotions = new ArrayList<>();

            List<SPCT> sanPhamChiTietList = spctRePo.findByidSanPham_IdIn(selectedProducts);

            if (sanPhamChiTietList.isEmpty()) {
                redirectAttributes.addFlashAttribute("errorMessage", "Không có sản phẩm mới nào được chọn.");
                return "redirect:/dot-giam-gia/view";
            }

            // Thêm các sản phẩm mới vào đợt giảm giá
            for (SPCT sanPhamChiTiet : sanPhamChiTietList) {
                DotGiamGiaChiTiet productDetailPromotion = new DotGiamGiaChiTiet();
                productDetailPromotion.setDotGiamGia(dotGiamGia);  // Liên kết đợt giảm giá
                productDetailPromotion.setSpct(sanPhamChiTiet);  // Liên kết sản phẩm chi tiết
                productDetailPromotion.setName(dotGiamGia.getName());  // Lấy tên của đợt giảm giá

                // Kiểm tra nếu sản phẩm chi tiết đã có mã, nếu chưa thì tạo mã mới
                if (productDetailPromotion.getCode() == null || productDetailPromotion.getCode().isEmpty()) {
                    String code = generateRandomCode();  // Tạo mã ngẫu nhiên cho đợt giảm giá
                    productDetailPromotion.setCode(code);
                }

                productDetailPromotion.setStatus(dotGiamGia.getStatus());
                productDetailPromotion.setCreateAt(dotGiamGia.getCreateAt());
                productDetailPromotion.setCreateBy(dotGiamGia.getCreateBy());
                productDetailPromotion.setDeleted(dotGiamGia.getDeleted());

                productDetailPromotions.add(productDetailPromotion);
            }

            // Lưu các chi tiết sản phẩm mới vào cơ sở dữ liệu
            if (!productDetailPromotions.isEmpty()) {
                productDetailPromotionRepo.saveAll(productDetailPromotions);
            }

            redirectAttributes.addFlashAttribute("successMessage", "Cập nhật đợt giảm giá thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Lỗi xảy ra khi cập nhật đợt giảm giá!");
            return "redirect:/dot-giam-gia/view";
        }

        return "redirect:/dot-giam-gia/view";
    }

    @PostMapping("/delete/{id}")
    public String deleteDotGiamGia(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        if (dotGiamGiaRepo.existsById(id)) {
            // Lấy đợt giảm giá
            DotGiamGia dotGiamGia = dotGiamGiaRepo.findById(id).orElse(null);

            if (dotGiamGia != null) {
                // Lấy danh sách các chi tiết sản phẩm liên quan đến đợt giảm giá
                List<DotGiamGiaChiTiet> dotGiamGiaChiTietList = dotGiamGia.getDotGiamGiaChiTietList();

                // Xoá các chi tiết sản phẩm liên quan
                if (!dotGiamGiaChiTietList.isEmpty()) {
                    productDetailPromotionRepo.deleteAll(dotGiamGiaChiTietList);
                    System.out.println("Deleted product details related to the discount batch.");
                }

                // Xoá đợt giảm giá
                dotGiamGiaRepo.deleteById(id);
                redirectAttributes.addFlashAttribute("successMessage", "Đợt giảm giá đã được xóa thành công!");
            }
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Không tìm thấy đợt giảm giá để xóa!");
        }
        return "redirect:/dot-giam-gia/view";
    }

}