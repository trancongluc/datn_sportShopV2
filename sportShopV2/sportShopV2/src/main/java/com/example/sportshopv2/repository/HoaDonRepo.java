package com.example.sportshopv2.repository;

import com.example.sportshopv2.dto.HistoryDTO;
import com.example.sportshopv2.model.HoaDon;
import com.example.sportshopv2.model.User;
import groovy.lang.Tuple;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface HoaDonRepo extends JpaRepository<HoaDon, Integer> {

    HoaDon findAllById(Integer id);

    @Query(value = "SELECT b.id, b.user_name, b.create_at, b.status, b.total_money, b.address, p.name " +
            "FROM bill b " +
            "JOIN account a ON a.id = b.id_account " +
            "JOIN [user] u ON b.id_staff = u.id " +
            "JOIN bill_detail bd ON b.ID = bd.id_bill " +
            "JOIN product_detail pd ON bd.id_product_detail = pd.ID " +
            "JOIN product p ON pd.id_product = p.ID " +
            "WHERE b.id_staff = :id", nativeQuery = true)
    List<Object[]> getHistory(Integer id);


}
