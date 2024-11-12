package com.example.sportshopv2.service;

import com.example.sportshopv2.Repository.ChatLieuRepository;
import com.example.sportshopv2.Repository.MauSacRepository;
import com.example.sportshopv2.model.ChatLieu;
import com.example.sportshopv2.model.MauSac;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MauSacService {
    private final MauSacRepository mauSacRepository;

    public List<MauSac> getAllMauSac( ) {
        return mauSacRepository.findAllByOrderByCreateAtDesc();
    }

    public MauSac addMauSac(MauSac mauSac) {
        mauSac.setCreateBy("NV1");
        mauSac.setTrangThai("Đang hoạt động");
        return mauSacRepository.save(mauSac);
    }

    public MauSac getMauSacById(Integer id) {
        Optional<MauSac> mauSacDetail = mauSacRepository.findById(id);
        return mauSacDetail.orElse(null);
    }

    public MauSac update(Integer id, MauSac mauSac) {
        return mauSacRepository.findById(id).map(ms -> {
            ms.setTenMauSac(mauSac.getTenMauSac());
            ms.setTrangThai(mauSac.getTrangThai());
            ms.setUpdateBy("CLT");
            ms.setUpdateAt(LocalDateTime.now());
            return mauSacRepository.save(ms);
        }).orElse(null);
    }
}
