package com.example.sportshopv2.service;


import com.example.sportshopv2.model.chatBox;
import com.example.sportshopv2.model.message;
import com.example.sportshopv2.repository.ChatBoxRepository;
import com.example.sportshopv2.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ChatService {
    @Autowired
    private ChatBoxRepository chatBoxRepository;

    @Autowired

    private MessageRepository messageRepository;

    // Tạo mới một ChatBox
    public chatBox createChatBox(String name, int createBy) {
        chatBox chatBox = new chatBox();
        chatBox.setName(name);
        chatBox.setCreateBy(createBy);
        chatBox.setCreateAt(LocalDateTime.now());
        chatBox.setDeleted(false);
        return chatBoxRepository.save(chatBox);
    }

    // Lấy danh sách các ChatBox chưa bị xóa
    public List<chatBox> getActiveChatBoxes() {
        return chatBoxRepository.findByDeletedFalse();
    }
    
    public message saveMessage(int chatBoxId, int accountId, String role, String content) {
    message mess = new message();
    chatBox chatBox = chatBoxRepository.findById(chatBoxId)
            .orElseThrow(() -> new IllegalArgumentException("Invalid chatBoxId: " + chatBoxId));
    mess.setChatBox(chatBox);
    mess.setAccountId(accountId);
    mess.setRole(role);
    mess.setContent(content);
    mess.setTimestamp(LocalDateTime.now());
    return messageRepository.save(mess);
}

    // Lưu tin nhắn vào Message
//    public message saveMessage(int chatBoxId, int accountId, String role, String content) {
//        message mess = new message();
//        mess.setChatBox.(chatBoxId);
//        mess.setAccountId(accountId);
//        mess.setRole(role);
//        mess.setContent(content);
//        mess.setTimestamp(LocalDateTime.now());
//        return messageRepository.save(mess);
//    }

    // Lấy lịch sử tin nhắn của một ChatBox
    public List<message> getMessagesByChatBoxId(int chatBoxId) {
        return messageRepository.findByChatBoxId(chatBoxId);
    }
}
