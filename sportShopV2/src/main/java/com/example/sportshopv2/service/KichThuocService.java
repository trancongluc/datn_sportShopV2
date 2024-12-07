package com.example.sportshopv2.service;

import com.example.sportshopv2.repository.KichThuocRepository;
import com.example.sportshopv2.model.KichThuoc;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class KichThuocService {
    private final KichThuocRepository kichThuocRepository;

    public List<KichThuoc> getAllKichThuoc() {
        return kichThuocRepository.findAllByOrderByCreateAtDesc();
    }

    public KichThuoc addKichThuoc(KichThuoc kichThuoc) {
        kichThuoc.setCreateBy("NV1");
        kichThuoc.setTrangThai("Đang hoạt động");
        return kichThuocRepository.save(kichThuoc);
    }

    public KichThuoc getKichThuocById(Integer id) {
        Optional<KichThuoc> kichThuocDetail = kichThuocRepository.findById(id);
        return kichThuocDetail.orElse(null);
    }
    public List<KichThuoc> findAllByIds(List<Integer> ids) {
        return kichThuocRepository.findAllById(ids);
    }
    public KichThuoc update(Integer id, KichThuoc kichThuoc) {
        return kichThuocRepository.findById(id).map(kt -> {
            kt.setTenKichThuoc(kichThuoc.getTenKichThuoc());
            kt.setTrangThai(kichThuoc.getTrangThai());
            kt.setUpdateBy("CLT");
            kt.setUpdateAt(LocalDateTime.now());
            return kichThuocRepository.save(kt);
        }).orElse(null);
    }
    public List<KichThuoc> getSizesNotInProduct(Integer productId) {
        return kichThuocRepository.findSizesNotInProduct(productId);
    }
}
