package com.example.sportshopv2.service.impl;

import com.example.sportshopv2.dto.SanPhamGiamGiaDTO;
import com.example.sportshopv2.model.*;
import com.example.sportshopv2.repository.DotGiamGiaRepo;
import com.example.sportshopv2.service.DotGiamGiaService;
import jakarta.transaction.Transactional;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;


@Service
public class DotGiamGiaServiceImp implements DotGiamGiaService {
    

    @Autowired
    private DotGiamGiaRepo dotGiamGiaRepo;

    @Override
    public List<DotGiamGia> getAll() {
        return dotGiamGiaRepo.findAll();
    }


    @Override
    public Boolean create(DotGiamGia dotGiamGia) {
        try {
            dotGiamGiaRepo.save(dotGiamGia);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public DotGiamGia findByID(Integer id) {
        return dotGiamGiaRepo.findById(id).orElse(null);
    }

    @Transactional
    public Boolean update(DotGiamGia dotGiamGia) {
        if (dotGiamGiaRepo.existsById(dotGiamGia.getId())) {
            dotGiamGia.setStatus(dotGiamGia.getStatus()); // Cập nhật trạng thái
            dotGiamGiaRepo.save(dotGiamGia);
            return true;
        }
        return false;
    }

    @Override
    public Boolean delete(Integer id) {
        if (dotGiamGiaRepo.existsById(id)) {
            dotGiamGiaRepo.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public void save(DotGiamGia dotGiamGia) {
        dotGiamGiaRepo.save(dotGiamGia);
    }

    @Override
    public List<SanPhamChiTiet> getSanPhamChiTietByDotGiamGiaId(Integer dotGiamGiaId) {
        return dotGiamGiaRepo.findSanPhamChiTietByDotGiamGiaId(dotGiamGiaId);
    }


}
