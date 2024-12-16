package com.example.sportshopv2.service.impl;

import com.example.sportshopv2.dto.HoaDonChiTietDTO;
import com.example.sportshopv2.model.HoaDon;
import com.example.sportshopv2.repository.HoaDonRepo;
import jakarta.persistence.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class HoaDonServiceImp {
    @Autowired
    private HoaDonRepo hoaDonRepo;
    @PersistenceContext
    private EntityManager entityManager;

    public HoaDon getBillDetailByBillCode(String tenHoaDon) {
        return hoaDonRepo.findBillDetailByBillCode(tenHoaDon);
    }

    public void updateHoaDon(Integer maHoaDon, HoaDon updatedHoaDon) {
        // Tìm hóa đơn theo ID
        HoaDon hoaDon = hoaDonRepo.findAllById(maHoaDon);
        if (hoaDon != null) {
            // Cập nhật các trường từ updatedHoaDon
            hoaDon.setStatus(updatedHoaDon.getStatus());
            hoaDon.setNote(updatedHoaDon.getNote());
            hoaDon.setCreateAt(LocalDateTime.now());
            // Cập nhật metadata
            hoaDon.setUpdateAt(LocalDateTime.now());
            hoaDon.setUpdate_by("system"); // hoặc từ tài khoản hiện tại
            // Lưu lại thông tin cập nhật
            hoaDonRepo.save(hoaDon);
        } else {
            throw new EntityNotFoundException("Hóa đơn với mã " + maHoaDon + " không tồn tại");
        }
    }

}