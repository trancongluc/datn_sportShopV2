package com.example.sportshopv2.service;


import com.example.sportshopv2.model.chatBox;
import com.example.sportshopv2.model.message;
import com.example.sportshopv2.repository.ChatBoxRepository;
import com.example.sportshopv2.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ChatService {
    //    @Autowired
//    private com.example.sportshopv2.repository.ChatBoxRepository chatBoxRepository;
//
//    @Autowired
//
//    private com.example.sportshopv2.repository.MessageRepository messageRepository;
//
//    // Tạo mới một ChatBox
//    public chatBox createChatBox(String name, int createBy) {
//        chatBox chatBox = new chatBox();
//        chatBox.setName(name);
//        chatBox.setCreateBy(createBy);
//        chatBox.setCreateAt(LocalDateTime.now());
//        chatBox.setDeleted(false);
//        return chatBoxRepository.save(chatBox);
//    }
//
//    // Lấy danh sách các ChatBox chưa bị xóa
//    public List<chatBox> getActiveChatBoxes() {
//        return chatBoxRepository.findByDeletedFalse();
//    }
//
//    // Lưu tin nhắn vào Message
//    public message saveMessage(int chatBoxId, int accountId, String role, String content) {
//        // Tạo một đối tượng message mới
//        message mess = new message();
//
//        // Tìm đối tượng chatBox từ chatBoxId
//        chatBox box = chatBoxRepository.findById(chatBoxId)
//                .orElseThrow(() -> new IllegalArgumentException("ChatBox không tồn tại với ID: " + chatBoxId));
//
//        // Thiết lập chatBox cho message
//        mess.setChatBox(box); // Giả sử bạn có phương thức setChatBox trong lớp message
//
//        mess.setAccountId(accountId);
//        mess.setRole(role);
//        mess.setContent(content);
//        mess.setTimestamp(LocalDateTime.now());
//
//        // Lưu message vào repository
//        return messageRepository.save(mess);
//    }
//
//    // Lấy lịch sử tin nhắn của một ChatBox
//    public List<message> getMessagesByChatBoxId(int chatBoxId) {
//        return messageRepository.findByChatBoxId(chatBoxId);
//    }
    @Autowired
    private ChatBoxRepository chatBoxRepository;

    @Autowired
    private MessageRepository messageRepository;

    // Tạo mới một ChatBox
//    public chatBox createChatBox(String name, int createBy) {
//        chatBox chatBox = new chatBox();
//        chatBox.setName(name);
//        chatBox.setCreateBy(createBy);
//        chatBox.setCreateAt(LocalDateTime.now());
//        chatBox.setDeleted(false);
//        return chatBoxRepository.save(chatBox);
//    }
//
//    // Lấy danh sách các ChatBox chưa bị xóa
//    public List<chatBox> getActiveChatBoxes() {
//        return chatBoxRepository.findByDeletedFalse();
//    }
//
//    // Lưu tin nhắn vào Message
//    public message saveMessage(int chatBoxId, int accountId, String role, String content) {
//        message mess = new message();
//
//        chatBox box = chatBoxRepository.findById(chatBoxId)
//                .orElseThrow(() -> new IllegalArgumentException("ChatBox không tồn tại với ID: " + chatBoxId));
//
//        mess.setChatBox(box);
//        mess.setAccountId(accountId);
//        mess.setRole(role);
//        mess.setContent(content);
//        mess.setTimestamp(LocalDateTime.now());
//
//        return messageRepository.save(mess);
//    }
//
//    // Lấy lịch sử tin nhắn của một ChatBox
//    public List<message> getMessagesByChatBoxId(int chatBoxId) {
//        return messageRepository.findByChatBoxId(chatBoxId);
//    }
//
//    // Tìm ChatBox theo accountId
//    public Optional<chatBox> getChatBoxByAccountId(int accountId) {
//        return chatBoxRepository.findByAccountId(accountId);
//    }


    public ChatService(ChatBoxRepository chatBoxRepository, MessageRepository messageRepository) {
        this.chatBoxRepository = chatBoxRepository;
        this.messageRepository = messageRepository;
    }

    // Lưu tin nhắn vào cơ sở dữ liệu
    public message saveMessage(int chatBoxId, int accountId, String role, String content) {
        // Kiểm tra chatbox có tồn tại không
        chatBox chatBox = chatBoxRepository.findById(chatBoxId)
                .orElseThrow(() -> new RuntimeException("ChatBox không tồn tại với ID: " + chatBoxId));

        // Tạo thực thể tin nhắn mới
        message message = new message();
        message.setChatBox(chatBox);
        message.setAccountId(accountId);
        message.setRole(role);
        message.setContent(content);
        message.setTimestamp(LocalDateTime.now());

        // Lưu vào cơ sở dữ liệu
        return messageRepository.save(message);
    }

    // Lấy danh sách tin nhắn theo ID chatbox
    public List<message> getMessagesByChatBoxId(int chatBoxId) {
        return messageRepository.findByChatBoxId(chatBoxId);
    }

    // Lấy danh sách các chatboxes đang hoạt động
    public List<chatBox> getActiveChatBoxes() {
        return chatBoxRepository.findAll(); // Hoặc tùy thuộc vào logic bạn cần
    }
}




