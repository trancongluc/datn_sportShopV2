package com.example.sportshopv2.service;

import com.example.sportshopv2.repository.DeGiayRepository;
import com.example.sportshopv2.model.DeGiay;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DeGiayService {
    private final DeGiayRepository   deGiayRepository;

    public Page<DeGiay> getAllDeGiay(Pageable pageable) {
        return deGiayRepository.findAllByOrderByCreateAtDesc(pageable);
    }
    public List<DeGiay> getAll() {
        return deGiayRepository.findAllByOrderByCreateAtDesc();
    }
    public DeGiay addDeGiay(DeGiay deGiay) {
        deGiay.setCreateBy("NV1");
        return deGiayRepository.save(deGiay);
    }

    public DeGiay getDeGiayById(Integer id) {
        Optional<DeGiay> deGiayDetail = deGiayRepository.findById(id);
        return deGiayDetail.orElse(null);
    }

    public DeGiay update(Integer id, DeGiay deGiay) {
        return deGiayRepository.findById(id).map(dg -> {
            dg.setTenDeGiay(deGiay.getTenDeGiay());
            dg.setTrangThai(deGiay.getTrangThai());
            dg.setUpdateBy("CLT");
            dg.setUpdateAt(LocalDateTime.now());
            return deGiayRepository.save(dg);
        }).orElse(null);
    }
}
