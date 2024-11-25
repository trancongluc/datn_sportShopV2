package com.example.sportshopv2.service;

import com.example.sportshopv2.model.DotGiamGia;
import com.example.sportshopv2.model.PhieuGiamGia;

import java.util.List;

public interface DotGiamGiaService {
    List<DotGiamGia> getAll();
    Boolean create(DotGiamGia dotGiamGia);
    DotGiamGia findByID(Integer id);
    Boolean update(DotGiamGia dotGiamGia);
    Boolean delete(Integer id);


    void save(DotGiamGia dotGiamGia);
}
