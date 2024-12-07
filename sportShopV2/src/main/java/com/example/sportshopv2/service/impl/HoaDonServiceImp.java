package com.example.sportshopv2.service.impl;

import com.example.sportshopv2.dto.HoaDonChiTietDTO;
import com.example.sportshopv2.model.HoaDon;
import com.example.sportshopv2.repository.HoaDonRepo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

@Service
public class HoaDonServiceImp {
    @Autowired
    private HoaDonRepo hoaDonRepo;
    @PersistenceContext
    private EntityManager entityManager;

    public HoaDon getBillDetailByBillCode(String tenHoaDon) {
        return hoaDonRepo.findBillDetailByBillCode(tenHoaDon);
    }

    public void updateTrangThai(Integer maHoaDon, String trangThai) {
        HoaDon hoaDon = hoaDonRepo.findAllById(maHoaDon);
        if (hoaDon != null) {
            hoaDon.setStatus(trangThai);
            hoaDonRepo.save(hoaDon);
        }
    }
}