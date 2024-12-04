package com.example.sportshopv2.service;

import com.example.sportshopv2.model.User;
import com.example.sportshopv2.repository.SanPhamChiTietRepository;
import com.example.sportshopv2.repository.SanPhamRepository;
import com.example.sportshopv2.model.SanPham;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SanPhamService {
    private final SanPhamRepository sanPhamRepository;
    private final SanPhamChiTietRepository spctRepo;

    public Page<SanPham> getAllSanPham(Pageable pageable) {
        return sanPhamRepository.findAllByOrderByCreateAtDesc(pageable);
    }

    public List<SanPham> findAllSanPham() {
        return sanPhamRepository.findAllByOrderByCreateAtDesc();
    }

    public SanPham findAllSanPhamById(Integer id) {
        return sanPhamRepository.findAllById(id);
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
            sp.setUpdateBy("CLT");
            sp.setUpdateAt(LocalDateTime.now());
            return sanPhamRepository.save(sp);
        }).orElse(null);
    }
    public Page<SanPham> searchSP(String keyword,String status, Pageable pageable) {
        return sanPhamRepository.searchSP(keyword,status, pageable);
    }




}
