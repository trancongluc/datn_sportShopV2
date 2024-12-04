package com.example.sportshopv2.service;

import com.example.sportshopv2.repository.ThuongHieuRepository;
import com.example.sportshopv2.model.ThuongHieu;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ThuongHieuService {
    private final ThuongHieuRepository thuongHieuRepository;

    public Page<ThuongHieu> getAllThuongHieu(Pageable pageable) {
        return thuongHieuRepository.findAllByOrderByCreateAtDesc(pageable);
    }
    public List<ThuongHieu> getAll() {
        return thuongHieuRepository.findAllByOrderByCreateAtDesc();
    }
    public ThuongHieu addThuongHieu(ThuongHieu thuongHieu) {
        thuongHieu.setCreateBy("NV1");
        return thuongHieuRepository.save(thuongHieu);
    }

    public ThuongHieu getThuongHieuById(Integer id) {
        Optional<ThuongHieu> thuongHieuDetail = thuongHieuRepository.findById(id);
        return thuongHieuDetail.orElse(null);
    }

    public ThuongHieu update(Integer id, ThuongHieu thuongHieu) {
        return thuongHieuRepository.findById(id).map(th -> {
            th.setTenThuongHieu(thuongHieu.getTenThuongHieu());
            th.setTrangThai(thuongHieu.getTrangThai());
            th.setUpdateBy("CLT");
            th.setUpdateAt(LocalDateTime.now());
            return thuongHieuRepository.save(th);
        }).orElse(null);
    }
}
