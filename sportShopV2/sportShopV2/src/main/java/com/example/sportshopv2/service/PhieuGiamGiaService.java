package com.example.sportshopv2.service;

import com.example.sportshopv2.model.PhieuGiamGia;

import java.util.List;

public interface PhieuGiamGiaService {
    List<PhieuGiamGia> getAll();
    Boolean create(PhieuGiamGia phieuGiamGia);
    PhieuGiamGia findByID(Integer id);
    Boolean update(PhieuGiamGia phieuGiamGia);
    Boolean delete(Integer id);
}
