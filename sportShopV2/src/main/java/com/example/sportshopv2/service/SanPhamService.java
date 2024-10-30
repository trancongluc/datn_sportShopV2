package com.example.sportshopv2.service;

import com.example.sportshopv2.Repository.ChatLieuRepository;
import com.example.sportshopv2.Repository.SanPhamRepository;
import com.example.sportshopv2.model.ChatLieu;
import com.example.sportshopv2.model.SanPham;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SanPhamService {
    private final SanPhamRepository sanPhamRepository;

    public Page<SanPham> getAllSanPham(Pageable pageable) {
        return sanPhamRepository.findAllByOrderByCreateAtDesc(pageable);
    }

    public SanPham addChatLieu(SanPham sanPham) {
        sanPham.setCreateBy("NV1");
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
