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

    public HoaDon getBillDetailByBillCode(String tenHoaDon) {
        String jpql = "SELECT b FROM HoaDon b JOIN b.account a JOIN a.nguoiDung u JOIN u.diaChi ad JOIN b.billDetails bd JOIN bd.sanPhamChiTiet pd JOIN pd.mauSac c JOIN pd.anhSP i WHERE b.bill_code = :tenHoaDon";

        TypedQuery<HoaDon> query = entityManager.createQuery(jpql, HoaDon.class);
        query.setParameter("tenHoaDon", tenHoaDon);

        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            throw new IllegalArgumentException("Không tìm thấy hóa đơn với ID: " + tenHoaDon, e);
        }
    }
    public void updateTrangThai(Integer maHoaDon, String trangThai) {
        HoaDon hoaDon = hoaDonRepo.findAllById(maHoaDon);
        if (hoaDon != null) {
            hoaDon.setStatus(trangThai);
            hoaDonRepo.save(hoaDon);
        }
    }
}