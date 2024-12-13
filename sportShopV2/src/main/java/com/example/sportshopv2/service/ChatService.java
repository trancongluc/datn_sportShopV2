package com.example.sportshopv2.service;


import com.example.sportshopv2.model.*;
import com.example.sportshopv2.repository.AccountRepo;
import com.example.sportshopv2.repository.ChatBoxRepository;
import com.example.sportshopv2.repository.MessageRepository;
import com.example.sportshopv2.repository.NguoiDungRepo;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ChatService {

    @Autowired
    private ChatBoxRepository chatBoxRepository;

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private AccountRepo accountRepository;

    @Autowired
    private NguoiDungRepo user;

    @Autowired
    private EntityManager entityManager;

    public ChatService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }
    @Transactional
    public message saveMessage(chatBox cb, int accountId, String role, String content) {
        // Tải lại cb vào ngữ cảnh Hibernate nếu cần
        if (!entityManager.contains(cb)) {
            cb = entityManager.merge(cb);
        }
        // Tạo mới đối tượng message
        message newMessage = new message();
        newMessage.setChatBox(cb);  // Đảm bảo rằng chatBox đã tồn tại
        newMessage.setAccountId(accountId);
        newMessage.setRole(role);
        newMessage.setContent(content);
        newMessage.setTimestamp(LocalDateTime.now());

        // Lưu tin nhắn vào cơ sở dữ liệu
        return messageRepository.save(newMessage);
    }

    public int getAccountIdFromUsername(String username) {
        // Tìm Account theo username
        Optional<Account> account = accountRepository.findByUsername(username);

        if (account.isPresent()) {
            return account.get().getId(); // Trả về accountId nếu tìm thấy
        } else {
            throw new RuntimeException("User not found");
        }
    }
    public int getNameFromIDUser(String username) {
        // Tìm Account theo username
        Optional<Account> account = accountRepository.findByUsername(username);

        if (account.isPresent()) {
            return account.get().getUser().getId(); // Trả về accountId nếu tìm thấy
        } else {
            throw new RuntimeException("User not found");
        }
    }
    public Optional<NguoiDung> getName(int ID){
        return user.findById(ID);
    }

    // Lấy danh sách tin nhắn theo ID chatbox
    public List<message> getMessagesByChatBoxId(int chatBoxId) {
        return messageRepository.findByChatBoxId(chatBoxId);
    }

    // Lấy danh sách các chatboxes đang hoạt động
    public List<chatBox> getActiveChatBoxes() {
        return chatBoxRepository.findAll(); // Hoặc tùy thuộc vào logic bạn cần
    }


    public List<message> getMesByAccountId(int accountId) {
        return messageRepository.findByAccountId(accountId);
    }

    public chatBox saveChatBox(chatBox chatBox) {
        return chatBoxRepository.save(chatBox);
    }


    public Optional<chatBox> findChatBoxByAccountId(Integer accountId) {
        // Truy vấn tất cả các ChatBox được tạo bởi accountId
        List<chatBox> chatBoxes = chatBoxRepository.findAllByCreateBy(accountId);

        if (chatBoxes.isEmpty()) {
            return Optional.empty(); // Không có ChatBox nào
        }

        // Lọc ChatBox hợp lệ (bỏ qua ChatBox có tên "noname")
        return chatBoxes.stream()
                .filter(box -> !"noname".equals(box.getName())) // Điều kiện lọc
                .findFirst(); // Trả về ChatBox hợp lệ đầu tiên nếu có
    }

}




