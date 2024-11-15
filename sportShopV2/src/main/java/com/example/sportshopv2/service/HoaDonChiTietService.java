package com.example.sportshopv2.service;

import com.example.sportshopv2.model.HoaDon;
import com.example.sportshopv2.model.HoaDonChiTiet;
import com.example.sportshopv2.repository.HoaDonChiTietRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class HoaDonChiTietService {
    private  final HoaDonChiTietRepo hdctRepo;
    public HoaDonChiTiet createHDCT(HoaDonChiTiet hdct){
        return hdctRepo.save(hdct);
    }
}
