package com.example.sportshopv2.service.impl;

import com.example.sportshopv2.model.PhieuGiamGia;
import com.example.sportshopv2.repository.PhieuGiamGiaResponsitory;
import com.example.sportshopv2.service.PhieuGiamGiaService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

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
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public PhieuGiamGia findByID(Integer id) {
        return phieuGiamGiaRepo.findById(id).orElse(null);
    }

    @Transactional
    public Boolean update(PhieuGiamGia phieuGiamGia) {
        if (phieuGiamGiaRepo.existsById(phieuGiamGia.getId())) {
            phieuGiamGia.setStatus(phieuGiamGia.getStatus()); // Cập nhật trạng thái
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

    @Override
    public List<PhieuGiamGia> getVoucherByGiaTriDonHang(Integer giaTriDonHang) {
        return phieuGiamGiaRepo.findVoucherByStatusAndMinimumValue("Đang diễn ra",giaTriDonHang);
    }

    @Override
    public void save(PhieuGiamGia giamGia) {
        phieuGiamGiaRepo.save(giamGia);
    }

    private String generateUniqueVoucherCode() {
        String code;
        do {
            code = "VC" + UUID.randomUUID().toString().substring(0, 5).toUpperCase();
        } while (phieuGiamGiaRepo.existsByVoucherCode(code));
        return code;
    }
}