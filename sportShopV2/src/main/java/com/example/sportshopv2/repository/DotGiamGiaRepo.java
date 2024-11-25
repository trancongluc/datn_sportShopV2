package com.example.sportshopv2.repository;

import com.example.sportshopv2.model.DotGiamGia;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DotGiamGiaRepo extends JpaRepository<DotGiamGia, Integer> {


//    boolean existsBySaleCode(String code);
}
