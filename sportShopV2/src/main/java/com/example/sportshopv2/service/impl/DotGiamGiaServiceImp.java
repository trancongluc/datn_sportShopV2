package com.example.sportshopv2.service.impl;

import com.example.sportshopv2.model.ChatLieu;
import com.example.sportshopv2.model.DotGiamGia;
import com.example.sportshopv2.model.PhieuGiamGia;
import com.example.sportshopv2.repository.DotGiamGiaRepo;
import com.example.sportshopv2.service.DotGiamGiaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
@Service
public class DotGiamGiaServiceImp implements DotGiamGiaService {
    @Autowired
    private DotGiamGiaRepo dotGiamGiaRepo;

    @Override
    public List<DotGiamGia> getAll() {
        return dotGiamGiaRepo.findAll();
    }


    @Override
    public Boolean create(DotGiamGia dotGiamGia) {
        try {
            // Tạo mã giảm giá duy nhất
            dotGiamGia.setSaleCode(generateUniqueVoucherCode());
            dotGiamGiaRepo.save(dotGiamGia);
            return true;
        } catch (Exception e) {
            e.printStackTrace(); // Có thể thay thế bằng logger
            return false;
        }
    }

    @Override
    public DotGiamGia findByID(Integer id) {
        return dotGiamGiaRepo.findById(id).orElse(null);
    }

    @Override
    public Boolean update(DotGiamGia dotGiamGia) {
        if (dotGiamGiaRepo.existsById(dotGiamGia.getId())) {
            dotGiamGiaRepo.save(dotGiamGia);
            return true;
        }
        return false;
    }

    @Override
    public Boolean delete(Integer id) {
        if (dotGiamGiaRepo.existsById(id)) {
            dotGiamGiaRepo.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public void save(DotGiamGia dotGiamGia) {
        dotGiamGiaRepo.save(dotGiamGia);
    }

    private String generateUniqueVoucherCode() {
        String code;
        do {
            code = "Sale" + UUID.randomUUID().toString().substring(0, 5).toUpperCase(); // Tạo mã ngẫu nhiên
        } while (dotGiamGiaRepo.existsBySaleCode(code)); // Kiểm tra xem mã đã tồn tại chưa
        return code;
    }


}
