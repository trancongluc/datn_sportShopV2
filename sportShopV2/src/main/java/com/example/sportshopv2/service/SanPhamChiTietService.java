package com.example.sportshopv2.service;

import com.example.sportshopv2.dto.SanPhamChiTietDTO;
import com.example.sportshopv2.model.SanPham;
import com.example.sportshopv2.model.SanPhamChiTiet;
import com.example.sportshopv2.repository.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SanPhamChiTietService {
    @Autowired
    private final SanPhamChiTietRepository sanPhamChiTietRepository;

    private final KichThuocRepository ktRepo;
    private final ChatLieuRepository clRepo;
    private final CoGiayRepository cgRepo;
    private final DeGiayRepository dgRepo;
    private final SanPhamRepository spRepo;
    private final TheLoaiRepository tlRepo;
    private final ThuongHieuRepository thRepo;
    private final MauSacRepository msRepo;

    private final AnhSanPhamRepository anhRepo;

    public SanPhamChiTiet addSPCT(SanPhamChiTiet spct) {
        spct.setCreateBy("NV1");
        return sanPhamChiTietRepository.save(spct);
    }

    public SanPhamChiTietDTO getByID(Integer idSPCT) {
        SanPhamChiTiet spct = sanPhamChiTietRepository.findByIdAndDeleted(idSPCT, false);
        return spct.toDTO(spct, ktRepo, spRepo, msRepo, thRepo, dgRepo, tlRepo, cgRepo, clRepo, anhRepo);
    }

    public List<SanPhamChiTietDTO> getAllDISTINCTSPCT() {
        List<SanPhamChiTiet> spct = sanPhamChiTietRepository.findDistinctByIdAndIdSanPham();
        return spct.stream().map(sanPhamChiTiet ->
                        SanPhamChiTiet.toDTO(sanPhamChiTiet, ktRepo, spRepo, msRepo, thRepo, dgRepo, tlRepo, cgRepo, clRepo, anhRepo))
                .collect(Collectors.toList());
    }

    public SanPhamChiTietDTO getSPCTByIDSPIDSIZEIDCOLOR(Integer idSP, Integer idSize, Integer idColor) {
        SanPhamChiTiet spct = sanPhamChiTietRepository.findAllByDeletedAndIdSanPhamAndIdKichThuocAndIdMauSac(false, idSP, idSize, idColor);
        return spct.toDTO(spct, ktRepo, spRepo, msRepo, thRepo, dgRepo, tlRepo, cgRepo, clRepo, anhRepo);
    }

    public SanPhamChiTiet findSPCTById(Integer idSPCT) {
        SanPhamChiTiet spct = sanPhamChiTietRepository.findByIdAndDeleted(idSPCT, false);
        return spct;
    }
    public SanPhamChiTietDTO findSPCTDtoById(Integer idSPCT) {
        SanPhamChiTiet spct = sanPhamChiTietRepository.findByIdAndDeleted(idSPCT, false);

        return spct.toDTO(spct, ktRepo, spRepo, msRepo, thRepo, dgRepo, tlRepo, cgRepo, clRepo, anhRepo);
    }
    public List<SanPhamChiTietDTO> getAllSPCT() {
        List<SanPhamChiTiet> listSPCT = sanPhamChiTietRepository.findAllByDeletedAndTrangThaiOrderByCreateAtDesc(false, "Đang hoạt động");
        return listSPCT.stream().map(sanPhamChiTiet ->
                        SanPhamChiTiet.toDTO(sanPhamChiTiet, ktRepo, spRepo, msRepo, thRepo, dgRepo, tlRepo, cgRepo, clRepo, anhRepo))
                .collect(Collectors.toList());
    }

    public List<SanPhamChiTietDTO> getAllSPCTBYID(
            List<Long> ids) {
        List<SanPhamChiTiet> listSPCT = sanPhamChiTietRepository.findByIdIn(ids);
        return listSPCT.stream().map(sanPhamChiTiet ->
                        SanPhamChiTiet.toDTO(sanPhamChiTiet, ktRepo, spRepo, msRepo, thRepo, dgRepo, tlRepo, cgRepo, clRepo, anhRepo))
                .collect(Collectors.toList());
    }

    public List<SanPhamChiTietDTO> findAllSPCTByIdSP(Integer idSP) {
        List<SanPhamChiTiet> listSPCT = sanPhamChiTietRepository.findAllByDeletedAndIdSanPham(false, idSP);
        return listSPCT.stream().map(sanPhamChiTiet ->
                        SanPhamChiTiet.toDTO(sanPhamChiTiet, ktRepo, spRepo, msRepo, thRepo, dgRepo, tlRepo, cgRepo, clRepo, anhRepo))
                .collect(Collectors.toList());
    }

    public List<SanPhamChiTiet> findAllByIdSP(Integer idSP) {
        List<SanPhamChiTiet> listSPCT = sanPhamChiTietRepository.findAllByDeletedAndIdSanPham(false, idSP);
        return listSPCT;
    }

    public Page<SanPhamChiTietDTO> getSPCTByIdSP(Integer idSP, Pageable pageable) {
        // Lấy trang danh sách SanPhamChiTiet từ repository
        Page<SanPhamChiTiet> listSPCT = sanPhamChiTietRepository.findAllByDeletedAndIdSanPham(false, idSP, pageable);
        // Ánh xạ từ SanPhamChiTiet sang SanPhamChiTietDTO
        return listSPCT.map(spct -> SanPhamChiTiet.toDTO(spct, ktRepo, spRepo, msRepo, thRepo, dgRepo, tlRepo, cgRepo, clRepo, anhRepo));
    }

    public List<SanPhamChiTietDTO> getListSPCTByIdSP(Integer idSP) {
        // Lấy trang danh sách SanPhamChiTiet từ repository
        List<SanPhamChiTiet> listSPCT = sanPhamChiTietRepository.findAllByDeletedAndIdSanPham(false, idSP);
        // Ánh xạ từ SanPhamChiTiet sang SanPhamChiTietDTO
        return listSPCT.stream().map(spct -> SanPhamChiTiet.toDTO(spct, ktRepo, spRepo, msRepo, thRepo, dgRepo, tlRepo, cgRepo, clRepo, anhRepo)).toList();
    }

    public boolean capNhatSoLuongSPCT(Integer idSPCT, Integer soLuongNew) {
        SanPhamChiTiet spct = sanPhamChiTietRepository.findByIdAndDeleted(idSPCT, false);
        if (spct == null) {
            return false;
        }
        spct.setSoLuong(soLuongNew);
        if (spct.getSoLuong() == 0) {
            spct.setTrangThai("Ngừng hoạt động");
        }
        sanPhamChiTietRepository.save(spct);
        return true;
    }

    public SanPhamChiTietDTO getProductDetailsById(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("ID không được null");
        }

        // Lấy sản phẩm từ repository
        SanPhamChiTiet spct = sanPhamChiTietRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Sản phẩm không tồn tại với ID: " + id));

        // Chuyển đổi sang DTO
        return SanPhamChiTiet.toDTO(spct, ktRepo, spRepo, msRepo,
                thRepo, dgRepo, tlRepo,
                cgRepo, clRepo, anhRepo);

    }

    public Integer tongSoLuongSP(Integer idSP) {
        Integer sLSanPham = sanPhamChiTietRepository.tongSoLuongSP(idSP);
        return sLSanPham;
    }

    public SanPhamChiTiet updateSanPhamChiTiet(Integer idSPCT, SanPhamChiTiet spct) {
        // Lấy sản phẩm chi tiết từ database
        Optional<SanPhamChiTiet> optionalSanPhamChiTiet = sanPhamChiTietRepository.findById(idSPCT);
        if (optionalSanPhamChiTiet.isEmpty()) {
            throw new RuntimeException("Sản phẩm chi tiết không tồn tại");
        }

        // Cập nhật thông tin sản phẩm chi tiết
        SanPhamChiTiet sanPhamChiTiet = optionalSanPhamChiTiet.get();
        sanPhamChiTiet.setIdKichThuoc(spct.getIdKichThuoc());
        sanPhamChiTiet.setIdSanPham(spct.getIdSanPham());
        sanPhamChiTiet.setIdMauSac(spct.getIdMauSac());
        sanPhamChiTiet.setIdThuongHieu(spct.getIdThuongHieu());
        sanPhamChiTiet.setIdDeGiay(spct.getIdDeGiay());
        sanPhamChiTiet.setIdTheLoai(spct.getIdTheLoai());
        sanPhamChiTiet.setIdCoGiay(spct.getIdCoGiay());
        sanPhamChiTiet.setIdChatLieu(spct.getIdChatLieu());
        sanPhamChiTiet.setMoTa(spct.getMoTa());
        sanPhamChiTiet.setSoLuong(spct.getSoLuong());
        sanPhamChiTiet.setGia(spct.getGia());
        sanPhamChiTiet.setGioiTinh(spct.getGioiTinh());
        sanPhamChiTiet.setTrangThai(spct.getTrangThai());

        // Lưu thông tin cập nhật vào database
        return sanPhamChiTietRepository.save(sanPhamChiTiet);
    }

    public SanPhamChiTiet updateSoLuongSanPhamChiTiet(Integer idSPCT, SanPhamChiTiet spct) {
        // Lấy sản phẩm chi tiết từ database
        Optional<SanPhamChiTiet> optionalSanPhamChiTiet = sanPhamChiTietRepository.findById(idSPCT);
        if (optionalSanPhamChiTiet.isEmpty()) {
            throw new RuntimeException("Sản phẩm chi tiết không tồn tại");
        }

        // Cập nhật thông tin sản phẩm chi tiết
        SanPhamChiTiet sanPhamChiTiet = optionalSanPhamChiTiet.get();
        sanPhamChiTiet.setSoLuong(spct.getSoLuong());
        sanPhamChiTiet.setGia(spct.getGia());
        sanPhamChiTiet.setGioiTinh(spct.getGioiTinh());
        sanPhamChiTiet.setUpdateAt(LocalDateTime.now());
        // Lưu thông tin cập nhật vào database
        return sanPhamChiTietRepository.save(sanPhamChiTiet);
    }
}
