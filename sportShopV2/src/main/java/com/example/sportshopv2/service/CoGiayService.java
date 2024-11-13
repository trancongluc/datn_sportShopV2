package com.example.sportshopv2.service;

import com.example.sportshopv2.repository.CoGiayRepository;
import com.example.sportshopv2.model.CoGiay;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CoGiayService {
    private final CoGiayRepository coGiayRepository;

    public Page<CoGiay> getAllCoGiay(Pageable pageable) {
        return coGiayRepository.findAllByOrderByCreateAtDesc(pageable);
    }
    public List<CoGiay> getAll() {
        return coGiayRepository.findAllByOrderByCreateAtDesc();
    }
    public CoGiay addCoGiay(CoGiay coGiay) {
        coGiay.setCreateBy("NV1");
        return coGiayRepository.save(coGiay);
    }

    public CoGiay getCoGiayById(Integer id) {
        Optional<CoGiay> coGiayDetail = coGiayRepository.findById(id);
        return coGiayDetail.orElse(null);
    }

    public CoGiay update(Integer id, CoGiay coGiay) {
        return coGiayRepository.findById(id).map(cg -> {
            cg.setTenCoGiay(coGiay.getTenCoGiay());
            cg.setTrangThai(coGiay.getTrangThai());
            cg.setUpdateBy("CLT");
            cg.setUpdateAt(LocalDateTime.now());
            return coGiayRepository.save(cg);
        }).orElse(null);
    }
}
