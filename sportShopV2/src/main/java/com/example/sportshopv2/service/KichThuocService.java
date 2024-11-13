package com.example.sportshopv2.service;

import com.example.sportshopv2.repository.KichThuocRepository;
import com.example.sportshopv2.model.KichThuoc;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class KichThuocService {
    private final KichThuocRepository kichThuocRepository;

    public Page<KichThuoc> getAllKichThuoc(Pageable pageable) {
        return kichThuocRepository.findAllByOrderByCreateAtDesc(pageable);
    }

    public KichThuoc addChatLieu(KichThuoc kichThuoc) {
        kichThuoc.setCreateBy("NV1");
        return kichThuocRepository.save(kichThuoc);
    }

    public KichThuoc getKichThuocById(Integer id) {
        Optional<KichThuoc> kichThuocDetail = kichThuocRepository.findById(id);
        return kichThuocDetail.orElse(null);
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
}
