package com.example.sportshopv2.Repository;

import com.example.sportshopv2.model.AnhSanPham;
import com.example.sportshopv2.model.ChatLieu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AnhSanPhamRepository extends JpaRepository<AnhSanPham, Integer> {


}
