package com.example.sportshopv2.service;

import com.example.sportshopv2.model.PhieuGiamGiaChiTiet;
import com.example.sportshopv2.repository.PhieuGiamGiaChiTietResponsitory;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PhieuGiamGiaChiTietService {
    private final PhieuGiamGiaChiTietResponsitory voucherDetailRepo;
    public PhieuGiamGiaChiTiet addVoucherDetail(PhieuGiamGiaChiTiet voucherDetail){
        return voucherDetailRepo.save(voucherDetail);
    }
}
