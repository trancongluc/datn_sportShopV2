package com.example.sportshopv2.service;

import com.example.sportshopv2.Repository.AnhSanPhamRepository;
import com.example.sportshopv2.Repository.ChatLieuRepository;
import com.example.sportshopv2.model.ChatLieu;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AnhService {
    private final AnhSanPhamRepository anhSanPhamRepository;


}
