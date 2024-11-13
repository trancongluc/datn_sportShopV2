package com.example.sportshopv2.service;

import com.example.sportshopv2.repository.TheLoaiRepository;
import com.example.sportshopv2.model.TheLoai;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TheLoaiService {
    private final TheLoaiRepository theLoaiRepository;

    public Page<TheLoai> getAllTheLoai(Pageable pageable) {
        return theLoaiRepository.findAllByOrderByCreateAtDesc(pageable);
    }
    public List<TheLoai> getAll() {
        return theLoaiRepository.findAllByOrderByCreateAtDesc();
    }
    public TheLoai addTheLoai(TheLoai theLoai) {
        theLoai.setCreateBy("NV1");
        return theLoaiRepository.save(theLoai);
    }

    public TheLoai getTheLoaiById(Integer id) {
        Optional<TheLoai> theLoaiDetail = theLoaiRepository.findById(id);
        return theLoaiDetail.orElse(null);
    }

    public TheLoai update(Integer id, TheLoai theLoai) {
        return theLoaiRepository.findById(id).map(tl -> {
            tl.setTenTheLoai(theLoai.getTenTheLoai());
            tl.setTrangThai(theLoai.getTrangThai());
            tl.setUpdateBy("CLT");
            tl.setUpdateAt(LocalDateTime.now());
            return theLoaiRepository.save(tl);
        }).orElse(null);
    }
}
