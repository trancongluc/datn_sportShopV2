package com.example.sportshopv2.service;


import com.example.sportshopv2.model.*;
import com.example.sportshopv2.repository.AccountRepo;
import com.example.sportshopv2.repository.ChatBoxRepository;
import com.example.sportshopv2.repository.MessageRepository;
import com.example.sportshopv2.repository.NguoiDungRepo;
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

    public ChatService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public message saveMessage(int chatBoxId, int accountId, String role, String content) {
        chatBox chatBox;

        // Kiểm tra nếu chatBox tồn tại, nếu không tạo mới
        if (chatBoxRepository.existsById(chatBoxId)) {
            chatBox = chatBoxRepository.findById(chatBoxId).orElseThrow(() ->
                    new RuntimeException("ChatBox not found"));
        } else {
            chatBox = new chatBox();
            chatBox.setId(chatBoxId); // Thiết lập id nếu cần thiết
            // Thiết lập các thuộc tính khác cho chatBox nếu cần
            chatBoxRepository.save(chatBox);
        }

        // Tạo mới đối tượng message
        message newMessage = new message();
        newMessage.setChatBox(chatBox);  // Đảm bảo rằng chatBox đã tồn tại
        newMessage.setAccountId(accountId);
        newMessage.setRole(role);
        newMessage.setContent(content);
        newMessage.setTimestamp(LocalDateTime.now());

        // Lưu tin nhắn vào cơ sở dữ liệu
        return messageRepository.save(newMessage);
    }

    public chatBox findChatBoxByAccountId(Integer accountId) {
        return chatBoxRepository.findByCreateBy(accountId);
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

    public Optional<NguoiDung> getName(int ID) {
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


//    public chatBox findChatBoxByAccountId(Integer accountId) {
//        return chatBoxRepository.findByCreateBy(accountId);
//    }


    public int getAccountFromUsername(String username) {
        // Tìm Account theo username
        Optional<Account> account = accountRepository.findByUsername(username);

        if (account.isPresent()) {
            return account.get().getUser().getId(); // Trả về accountId nếu tìm thấy
        } else {
            throw new RuntimeException("User not found");
        }
    }

    public int getAccountId(String username) {
        // Tìm Account theo username
        Optional<Account> account = accountRepository.findByUsername(username);

        if (account.isPresent()) {
            return account.get().getId(); // Trả về accountId nếu tìm thấy
        } else {
            throw new RuntimeException("User not found");
        }
    }

    public boolean isChatBoxExistsByCreateBy(int accountId) {
        return chatBoxRepository.existsByCreateBy(accountId);
    }
}




