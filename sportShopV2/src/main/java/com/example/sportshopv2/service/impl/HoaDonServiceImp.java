package com.example.sportshopv2.service.impl;

import com.example.sportshopv2.dto.HoaDonChiTietDTO;
import com.example.sportshopv2.model.HoaDon;
import com.example.sportshopv2.repository.HoaDonRepo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HoaDonServiceImp {
    @Autowired
    private HoaDonRepo hoaDonRepo;
    @PersistenceContext
    private EntityManager entityManager;

    public HoaDon getBillDetailById(Integer maHoaDon) {
        String jpql = "SELECT b FROM HoaDon b JOIN b.account a JOIN a.nguoiDung u JOIN u.diaChi ad JOIN b.billDetails bd JOIN bd.sanPhamChiTiet pd JOIN pd.mauSac c JOIN pd.anhSP i WHERE b.id = :maHoaDon";

        TypedQuery<HoaDon> query = entityManager.createQuery(jpql, HoaDon.class);
        query.setParameter("maHoaDon", maHoaDon);

        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            throw new IllegalArgumentException("Không tìm thấy hóa đơn với ID: " + maHoaDon, e);
        }
    }
    public void updateTrangThai(Integer maHoaDon, String trangThai) {
        HoaDon hoaDon = hoaDonRepo.findById(maHoaDon).orElse(null);
        if (hoaDon != null) {
            hoaDon.setStatus(trangThai);
            hoaDonRepo.save(hoaDon);
        }
    }
}