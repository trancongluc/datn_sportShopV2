package com.example.sportshopv2.repository;

import com.example.sportshopv2.model.NguoiDung;
import com.example.sportshopv2.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface NguoiDungRepo extends JpaRepository<NguoiDung, Integer> {
    @Query("SELECT nd FROM TaiKhoan tk " +
            "JOIN tk.nguoiDung nd " +
            "WHERE tk.role = 'Employee' AND nd.deleted = false " +
            "ORDER BY nd.create_at DESC")
    List<NguoiDung> findAllKhachHang();



}
