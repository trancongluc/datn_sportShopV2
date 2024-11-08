package com.example.sportshopv2.service;

import com.example.sportshopv2.Repository.*;
import com.example.sportshopv2.dto.SanPhamChiTietDTO;
import com.example.sportshopv2.model.SanPhamChiTiet;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SanPhamChiTietService {
    private final SanPhamChiTietRepository sanPhamChiTietRepository;
    private final KichThuocRepository ktRepo;
    private final ChatLieuRepository clRepo;
    private final CoGiayRepository cgRepo;
    private final DeGiayRepository dgRepo;
    private final SanPhamRepository spRepo;
    private final TheLoaiRepository tlRepo;
    private final ThuongHieuRepository thRepo;
    private final MauSacRepository msRepo;

    public SanPhamChiTiet addSPCT(SanPhamChiTiet spct) {
        spct.setCreateBy("NV1");
        spct.setTrangThai("Đang hoạt động");
        return sanPhamChiTietRepository.save(spct);
    }

    public Page<SanPhamChiTietDTO> getSPCTByIdSP(Integer idSP, Pageable pageable) {
        // Lấy trang danh sách SanPhamChiTiet từ repository
        Page<SanPhamChiTiet> listSPCT = sanPhamChiTietRepository.findAllByDeletedAndIdSanPham(false, idSP, pageable);

        // Ánh xạ từ SanPhamChiTiet sang SanPhamChiTietDTO
        return listSPCT.map(spct -> SanPhamChiTiet.toDTO(spct, ktRepo, spRepo, msRepo, thRepo, dgRepo, tlRepo, cgRepo, clRepo));
    }


}
