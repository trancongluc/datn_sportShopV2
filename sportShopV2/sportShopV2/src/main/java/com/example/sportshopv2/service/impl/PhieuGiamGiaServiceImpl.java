package com.example.sportshopv2.service.impl;

import com.example.sportshopv2.model.PhieuGiamGia;
import com.example.sportshopv2.repository.PhieuGiamGiaResponsitory;
import com.example.sportshopv2.service.PhieuGiamGiaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PhieuGiamGiaServiceImpl implements PhieuGiamGiaService {

    @Autowired
    private PhieuGiamGiaResponsitory phieuGiamGiaRepo;

    @Override
    public List<PhieuGiamGia> getAll() {
        return phieuGiamGiaRepo.findAll();
    }

    @Override
    public Boolean create(PhieuGiamGia phieuGiamGia) {
        try {
            phieuGiamGiaRepo.save(phieuGiamGia);
            return true;
        } catch (Exception e) {
            return false; // Có thể log lỗi ở đây
        }
    }

    @Override
    public PhieuGiamGia findByID(Integer id) {
        return phieuGiamGiaRepo.findById(id).orElse(null);
    }

    @Override
    public Boolean update(PhieuGiamGia phieuGiamGia) {
        if (phieuGiamGiaRepo.existsById(phieuGiamGia.getId())) {
            phieuGiamGiaRepo.save(phieuGiamGia);
            return true;
        }
        return false;
    }

    @Override
    public Boolean delete(Integer id) {
        if (phieuGiamGiaRepo.existsById(id)) {
            phieuGiamGiaRepo.deleteById(id);
            return true;
        }
        return false;
    }
}