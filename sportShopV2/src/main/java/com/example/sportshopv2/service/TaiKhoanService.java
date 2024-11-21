package com.example.sportshopv2.service;

import com.example.sportshopv2.model.NguoiDung;
import com.example.sportshopv2.model.TaiKhoan;
import com.example.sportshopv2.repository.NguoiDungRepo;
import com.example.sportshopv2.repository.TaiKhoanRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TaiKhoanService {
    private final TaiKhoanRepo tkRepo;
    private final NguoiDungRepo nguoiDungRepo;

    public TaiKhoan getTKByUser(Integer idUser) {
        NguoiDung user = nguoiDungRepo.findById(idUser).orElse(null);
        return tkRepo.findByNguoiDung(user);
    }
    public TaiKhoan createTK(TaiKhoan taiKhoan) {

       return tkRepo.save(taiKhoan);
    }

}
