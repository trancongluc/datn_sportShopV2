package com.example.sportshopv2.service;

import com.example.sportshopv2.repository.*;
import com.example.sportshopv2.dto.SanPhamChiTietDTO;
import com.example.sportshopv2.entity.SanPhamChiTiet;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

    private final AnhSanPhamRepository anhRepo;

    public SanPhamChiTiet addSPCT(SanPhamChiTiet spct) {
        spct.setCreateBy("NV1");
        spct.setTrangThai("Đang hoạt động");
        return sanPhamChiTietRepository.save(spct);
    }

    public SanPhamChiTietDTO getByID(Integer idSPCT) {
        SanPhamChiTiet spct = sanPhamChiTietRepository.findByIdAndDeleted(idSPCT, false);
        return spct.toDTO(spct, ktRepo, spRepo, msRepo, thRepo, dgRepo, tlRepo, cgRepo, clRepo, anhRepo);
    }

    public List<SanPhamChiTietDTO> getAllSPCT() {
        List<SanPhamChiTiet> listSPCT = sanPhamChiTietRepository.findAllByDeleted(false);
        return listSPCT.stream().map(sanPhamChiTiet ->
                        SanPhamChiTiet.toDTO(sanPhamChiTiet, ktRepo, spRepo, msRepo, thRepo, dgRepo, tlRepo, cgRepo, clRepo, anhRepo))
                .collect(Collectors.toList());
    }

    public Page<SanPhamChiTietDTO> getSPCTByIdSP(Integer idSP, Pageable pageable) {
        // Lấy trang danh sách SanPhamChiTiet từ repository
        Page<SanPhamChiTiet> listSPCT = sanPhamChiTietRepository.findAllByDeletedAndIdSanPham(false, idSP, pageable);

        // Ánh xạ từ SanPhamChiTiet sang SanPhamChiTietDTO
        return listSPCT.map(spct -> SanPhamChiTiet.toDTO(spct, ktRepo, spRepo, msRepo, thRepo, dgRepo, tlRepo, cgRepo, clRepo, anhRepo));
    }


}
