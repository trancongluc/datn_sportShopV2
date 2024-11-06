package com.example.sportshopv2.service;

import com.example.sportshopv2.Repository.SanPhamChiTietRepository;
import com.example.sportshopv2.model.SanPhamChiTiet;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SanPhamChiTietService {
    private final SanPhamChiTietRepository sanPhamChiTietRepository;

    public SanPhamChiTiet addSPCT(SanPhamChiTiet spct) {
        spct.setCreateBy("NV1");
        spct.setTrangThai("Đang hoạt động");
        return sanPhamChiTietRepository.save(spct);
    }
}
