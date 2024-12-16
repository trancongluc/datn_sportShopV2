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
        model.addAttribute("listDotGiamGia", dotGiamGiaRepo.findAll(Sort.by(Sort.Direction.DESC, "id")));
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


    //    @PostMapping("/save")
//    public String saveDotGiamGia(
//            @RequestParam List<Long> selectedProducts,
//            @ModelAttribute DotGiamGia dotGiamGia,
//            RedirectAttributes redirectAttributes) {
//
//
//        List<SanPhamChiTiet> updatedProducts = new ArrayList<>();
//
//        // Lưu giá gốc vào cache và cập nhật giá sản phẩm
//        for (Long productId : selectedProducts) {
//            Optional<SanPhamChiTiet> sanPhamChiTietOpt = SPCTRep.findActiveById(productId.intValue());
//            sanPhamChiTietOpt.ifPresent(sanPhamChiTiet -> {
//
//                originalPricesCache.put(productId,100.f);
//
//                float newPrice = sanPhamChiTiet.getGia() - dotGiamGia.getDiscount().floatValue();
//                sanPhamChiTiet.setGia(newPrice);
//
//                updatedProducts.add(sanPhamChiTiet);
//            });
//        }
//
//        // Lưu đợt giảm giá
//        dotGiamGiaRepo.save(dotGiamGia);
//
//        // Lưu các sản phẩm đã cập nhật
//        SPCTRep.saveAll(updatedProducts);
//        redirectAttributes.addFlashAttribute("successMessage", "Lưu đợt giảm giá thành công!");
//        return "redirect:/dot-giam-gia/view";
//    }
    @PostMapping("/save")
    public String saveDotGiamGia(
            @RequestParam("selectProductID") List<Long> selectedProducts,
            @ModelAttribute DotGiamGia dotGiamGia,
            RedirectAttributes redirectAttributes) {


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
        for (DotGiamGiaChiTiet dotGiamGiaChiTiet : dotGiamGiaChiTiets
        ) {
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
            dotGiamGiaService.update(dotGiamGia); // Sử dụng service để cập nhật

            BigDecimal discountValue = dotGiamGia.getDiscount();
            float discountFloat = discountValue.floatValue();

            List<SanPhamChiTiet> updatedProducts = new ArrayList<>();

            for (Long productId : selectedProducts) {
                Optional<SanPhamChiTiet> sanPhamChiTietOpt = SPCTRep.findActiveById(productId.intValue());
                sanPhamChiTietOpt.ifPresent(sanPhamChiTiet -> {
                    float oldPrice = sanPhamChiTiet.getGia();
                    float newPrice = oldPrice - discountFloat;
                    sanPhamChiTiet.setGia(newPrice);
                    updatedProducts.add(sanPhamChiTiet);
                });
            }

            if (!updatedProducts.isEmpty()) {
                SPCTRep.saveAll(updatedProducts);
            }

            redirectAttributes.addFlashAttribute("successMessage", "Cập nhật thành công!");
        } catch (NumberFormatException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Giá trị giảm không hợp lệ!");
            return "redirect:/dot-giam-gia/view";
        }

        return "redirect:/dot-giam-gia/view";
    }


    @PostMapping("/delete/{id}")
    public String deleteDotGiamGia(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        if (dotGiamGiaRepo.existsById(id)) {
            // Lấy đợt giảm giá để lấy danh sách sản phẩm liên quan
            DotGiamGia dotGiamGia = dotGiamGiaRepo.findById(id).orElse(null);
            if (dotGiamGia != null) {
                List<DotGiamGiaChiTiet> dotGiamGiaChiTietList = dotGiamGia.getDotGiamGiaChiTietList(); // Lấy danh sách sản phẩm liên quan

                List<SanPhamChiTiet> updatedProducts = new ArrayList<>();

                for (DotGiamGiaChiTiet chiTiet : dotGiamGiaChiTietList) {
                    SPCT sanPhamChiTiet = chiTiet.getSpct();
                    // Khôi phục giá gốc từ cache
                    Float originalPrice = originalPricesCache.get(sanPhamChiTiet.getId());
                    if (originalPrice != null) {
                        sanPhamChiTiet.setGia(originalPrice);
//                        updatedProducts.add(sanPhamChiTiet); // Thêm sản phẩm đã cập nhật vào danh sách
                    }
                }

                // Lưu các sản phẩm đã cập nhật
                if (!updatedProducts.isEmpty()) {
                    SPCTRep.saveAll(updatedProducts);
                }

                // Xóa đợt giảm giá
                dotGiamGiaRepo.deleteById(id);
                redirectAttributes.addFlashAttribute("successMessage", "Xóa đợt giảm giá thành công và giá sản phẩm đã được khôi phục!");
            }
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Không tìm thấy đợt giảm giá để xóa!");
        }
        return "redirect:/dot-giam-gia/view";
    }
}