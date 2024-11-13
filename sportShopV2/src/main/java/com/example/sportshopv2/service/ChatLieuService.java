package com.example.sportshopv2.service;

import com.example.sportshopv2.repository.ChatLieuRepository;
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
public class ChatLieuService {
    private final ChatLieuRepository chatLieuRepository;

    public Page<ChatLieu> getAllChatLieu(Pageable pageable) {
        return chatLieuRepository.findAllByOrderByCreateAtDesc(pageable);
    }
    public List<ChatLieu> getAll() {
        return chatLieuRepository.findAllByOrderByCreateAtDesc();
    }
    public ChatLieu addChatLieu(ChatLieu chatLieu) {
        chatLieu.setCreateBy("NV1");
        return chatLieuRepository.save(chatLieu);
    }

    public ChatLieu getChatLieuById(Integer id) {
        Optional<ChatLieu> chatLieuDetail = chatLieuRepository.findById(id);
        return chatLieuDetail.orElse(null);
    }

    public ChatLieu update(Integer id, ChatLieu chatLieu) {
        return chatLieuRepository.findById(id).map(cl -> {
            cl.setTenChatLieu(chatLieu.getTenChatLieu());
            cl.setTrangThai(chatLieu.getTrangThai());
            cl.setUpdateBy("CLT");
            cl.setUpdateAt(LocalDateTime.now());
            return chatLieuRepository.save(cl);
        }).orElse(null);
    }
}
