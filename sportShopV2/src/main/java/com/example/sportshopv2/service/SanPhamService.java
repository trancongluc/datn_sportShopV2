package com.example.sportshopv2.service;

import com.example.sportshopv2.repository.SanPhamRepository;
import com.example.sportshopv2.entity.SanPham;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SanPhamService {
    private final SanPhamRepository sanPhamRepository;

    public Page<SanPham> getAllSanPham(Pageable pageable) {
        return sanPhamRepository.findAllByOrderByCreateAtDesc(pageable);
    }
    private String generateProductCode() {
        return "SP-" + UUID.randomUUID().toString();
    }
    public SanPham addSanPham(SanPham sanPham) {
        String codeSP = generateProductCode();
        sanPham.setCreateBy("NV1");
        sanPham.setCode(codeSP);
        sanPham.setTrangThai("Đang hoạt động");
        return sanPhamRepository.save(sanPham);
    }

    public SanPham getSanPhamById(Integer id) {
        Optional<SanPham> sanPhamDetail = sanPhamRepository.findById(id);
        return sanPhamDetail.orElse(null);
    }

    public SanPham update(Integer id, SanPham sanPham) {
        return sanPhamRepository.findById(id).map(sp -> {
            sp.setTenSanPham(sanPham.getTenSanPham());
            sp.setTrangThai(sanPham.getTrangThai());
            sp.setUpdateBy("CLT");
            sp.setUpdateAt(LocalDateTime.now());
            return sanPhamRepository.save(sp);
        }).orElse(null);
    }
}
