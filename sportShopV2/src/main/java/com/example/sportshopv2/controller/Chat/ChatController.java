package com.example.sportshopv2.controller.Chat;


import com.example.sportshopv2.model.chatBox;
import com.example.sportshopv2.model.message;
import com.example.sportshopv2.repository.ChatBoxRepository;
import com.example.sportshopv2.repository.MessageRepository;
import com.example.sportshopv2.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.*;

@Controller
@RequestMapping("/")
public class ChatController {


//    @Autowired
//    private ChatService chatService;
//    @Autowired
//    private SimpMessagingTemplate messagingTemplate;
//    @Autowired
//    private ChatBoxRepository chatBoxRepository;
//    @Autowired
//    private MessageRepository messageRepository;
//
//    @GetMapping()
//    public String getInterface() {
//        return "Chat/chat";
//    }
//
//    // Hàm tạo ChatBox mới
//    public chatBox createChatBox(String name) {
//        chatBox chatBox = new chatBox();
//        chatBox.setName(name);
//        chatBox.setCreateAt(LocalDateTime.now());
//        chatBox.setCreateBy(0); // ID người tạo (0 nếu không xác định)
//        chatBox.setDeleted(false);
//
//        return chatBoxRepository.save(chatBox);
//    }
//
//    // Hàm lấy ChatBox theo ID
//    public Optional<chatBox> getChatBoxById(int chatBoxId) {
//        return chatBoxRepository.findById(chatBoxId);
//    }
//
//    // Hàm lưu Message
//    public message saveMessage(int chatBoxId, int accountId, String role, String content) {
//        message message = new message();
//        message.setChatBox(chatBoxRepository.findById(chatBoxId).orElseThrow());
//        message.setAccountId(accountId);
//        message.setRole(role);
//        message.setContent(content);
//        message.setTimestamp(LocalDateTime.now());
//
//        return messageRepository.save(message);
//    }
//
//    @MessageMapping("/chat") // Endpoint xử lý tin nhắn WebSocket
//    @SendTo("/topic/messages") // Phát tin nhắn đến topic chung
//    public message handleChatMessage(message chatMessage) {
//        // Lưu tin nhắn vào database
//        message savedMessage = chatService.saveMessage(
//                chatMessage.getChatBox().getId(),
//                chatMessage.getAccountId(),
//                chatMessage.getRole(),
//                chatMessage.getContent()
//        );
//        // Trả về tin nhắn để phát tới các client đã đăng ký
//        return savedMessage;
//    }
//
//    /**
//     * Gửi tin nhắn từ phía Shop đến các client
//     */
//    @PostMapping("/send")
//    public void sendMessageFromShop(@RequestBody message message) {
//        System.out.println("Shop gửi tin nhắn: " + message);
//
//        // Lưu tin nhắn vào database
//        message savedMessage = chatService.saveMessage(
//                message.getChatBox().getId(),
//                message.getAccountId(),
//                "shop",
//                message.getContent()
//        );
//
//        // Phát tin nhắn đến các client đã đăng ký topic "/topic/messages"
//        messagingTemplate.convertAndSend("/topic/messages", savedMessage);
//    }
//
//    /**
//     * Lấy danh sách các hộp chat đang hoạt động
//     */
//    @GetMapping("/chatboxes")
//    @ResponseBody
//    public List<chatBox> getActiveChatBoxes() {
//        // Truy vấn danh sách chat box hoạt động từ service
//        return chatService.getActiveChatBoxes();
//    }
//
//    /**
//     * Lấy danh sách tin nhắn của một chat box cụ thể
//     */
//    @GetMapping("/messages/{chatBoxId}")
//    @ResponseBody
//    public List<message> getMessagesByChatBoxId(@PathVariable int chatBoxId) {
    // Truy vấn tin nhắn của chat box từ service
//        return chatService.getMessagesByChatBoxId(chatBoxId);
//    }


    @Autowired
    private ChatService chatService;
    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    @Autowired
    private ChatBoxRepository chatBoxRepository;
    @Autowired
    private MessageRepository messageRepository;

//    @GetMapping()
//    public String getInterface() {
//        return "Chat/chat"; // Trang giao diện chat cho cả Shop và Client
//    }
//
//    /**
//     * Tự động tạo ChatBox mới khi phía client gửi tin nhắn mà không có ChatBox
//     */
//    private chatBox getOrCreateChatBox(int accountId) {
//        return chatService.getChatBoxByAccountId(accountId)
//                .orElseGet(() -> chatService.createChatBox("ChatBox for Account " + accountId, accountId));
//    }
//
//
//    /**
//     * Lưu tin nhắn vào cơ sở dữ liệu
//     */
//    public message saveMessage(int chatBoxId, int accountId, String role, String content) {
//        return chatService.saveMessage(chatBoxId, accountId, role, content);
//    }
//
//    @MessageMapping("/chat") // Endpoint xử lý tin nhắn WebSocket
//    @SendTo("/topic/messages") // Phát tin nhắn đến topic chung
//    public message handleChatMessage(message chatMessage) {
//        // Lấy ChatBox hoặc tự động tạo mới nếu không có
//        chatBox chatBox = getOrCreateChatBox(chatMessage.getAccountId());
//
//        // Lưu tin nhắn vào database
//        message savedMessage = saveMessage(chatBox.getId(), chatMessage.getAccountId(), chatMessage.getRole(), chatMessage.getContent());
//
//        // Trả về tin nhắn để phát tới các client đã đăng ký
//        return savedMessage;
//    }
//
//    /**
//     * Gửi tin nhắn từ phía Shop đến các client
//     */
//    @PostMapping("/send")
//    public void sendMessageFromShop(@RequestBody message message) {
//        System.out.println("Shop gửi tin nhắn: " + message);
//
//        // Lấy ChatBox hoặc tạo mới nếu không tồn tại
//        chatBox chatBox = getOrCreateChatBox(message.getAccountId());
//
//        // Lưu tin nhắn vào database
//        message savedMessage = saveMessage(chatBox.getId(), message.getAccountId(), "shop", message.getContent());
//
//        // Phát tin nhắn đến các client đã đăng ký topic "/topic/messages"
//        messagingTemplate.convertAndSend("/topic/messages", savedMessage);
//    }
//    /**
//     * Lấy danh sách các hộp chat đang hoạt động
//     */
//    @GetMapping("/chatboxes")
//    @ResponseBody
//    public List<chatBox> getActiveChatBoxes() {
//        // Truy vấn danh sách chat box hoạt động từ service
//        return chatService.getActiveChatBoxes();
//    }
//
//    /**
//     * Lấy danh sách tin nhắn của một chat box cụ thể
//     */
//    @GetMapping("/messages/{chatBoxId}")
//    @ResponseBody
//    public List<message> getMessagesByChatBoxId(@PathVariable int chatBoxId) {
//        // Truy vấn tin nhắn của chat box từ service
//        return chatService.getMessagesByChatBoxId(chatBoxId);
//    }

//    @GetMapping("")
//    public String getInterface() {
//        return "Chat/chat";
//    }


    @GetMapping("/favicon.ico")
    @ResponseBody
    void disableFavicon() {
        // Không làm gì cả để tránh lỗi
    }

    // Xử lý gửi tin nhắn (POST)
    // Xử lý tin nhắn từ client (gửi qua WebSocket)
//    @MessageMapping("/sendMessage")
//    @SendTo("/topic/messages")
//    public message handleMessage(@Payload message newMessage) {
//        // Lưu tin nhắn vào cơ sở dữ liệu
//        chatService.saveMessage(
//                newMessage.getChatBox().getId(),
//                newMessage.getAccountId(),
//                newMessage.getRole(),
//                newMessage.getContent()
//        );
//
//        // Trả về tin nhắn để broadcast tới tất cả các client
//        return newMessage;
//    }
    // Hiển thị giao diện chat chính
    @GetMapping("")
    public String showChatPage(Model model) {
        // Lấy danh sách tất cả chatboxes
        List<chatBox> chatBoxes = chatService.getActiveChatBoxes();
        model.addAttribute("chatBoxes", chatBoxes);

        // Lấy tin nhắn cho từng chatbox
        Map<Integer, List<message>> messagesByChatBox = new HashMap<>();
        for (chatBox chatBox : chatBoxes) {
            List<message> messages = chatService.getMessagesByChatBoxId(chatBox.getId());
            messagesByChatBox.put(chatBox.getId(), messages);
        }
        model.addAttribute("messagesByChatBox", messagesByChatBox);

        return "Chat/chat";
    }

    // Xử lý tin nhắn gửi qua WebSocket
    @MessageMapping("/sendMessage")
    @SendTo("/topic/messages")
    public message handleMessage(@Payload message newMessage) {
        // Debug: In ra thông tin tin nhắn
        System.out.println("Received message: " + newMessage.getContent());
        try {
            // Lưu tin nhắn vào cơ sở dữ liệu
            message savedMessage = chatService.saveMessage(
                    newMessage.getChatBox().getId(),
                    newMessage.getAccountId(),
                    newMessage.getRole(),
                    newMessage.getContent()
            );

            return savedMessage; // Trả về tin nhắn đã lưu để broadcast tới các client khác
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi lưu tin nhắn: " + e.getMessage());
        }
    }

    // API để lấy danh sách tin nhắn theo chatbox
    @GetMapping("/messages/{chatBoxId}")
    @ResponseBody
    public List<message> getMessagesByChatBoxId(@PathVariable int chatBoxId) {
        return chatService.getMessagesByChatBoxId(chatBoxId);
    }

}




