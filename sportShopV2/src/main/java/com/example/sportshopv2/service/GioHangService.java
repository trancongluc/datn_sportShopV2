package com.example.sportshopv2.service;

import com.example.sportshopv2.repository.GioHangChiTietRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GioHangService {
    @Autowired
    GioHangChiTietRepo gioHangChiTietRepo;

    @Transactional
    public void xoaSanPhamHetHang(List<Long> outOfStockProductIds) {
        gioHangChiTietRepo.deleteBySanPhamChiTietIds(outOfStockProductIds);
    }
}

