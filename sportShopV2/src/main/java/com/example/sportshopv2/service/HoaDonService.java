package com.example.sportshopv2.service;

import com.example.sportshopv2.model.HoaDon;
import com.example.sportshopv2.repository.HoaDonRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class HoaDonService {
    private final HoaDonRepo hdRepo;
    public HoaDon createHoaDon(HoaDon hoaDon){
        return hdRepo.save(hoaDon);
    }
}
