package com.example.sportshopv2.controller.Chat;


import com.example.sportshopv2.model.chatBox;
import com.example.sportshopv2.model.message;
import com.example.sportshopv2.repository.ChatBoxRepository;
import com.example.sportshopv2.repository.MessageRepository;
import com.example.sportshopv2.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.util.*;

@Controller
@RequestMapping("/")
public class ChatController {

    @Autowired
    private ChatService chatService;
    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    @Autowired
    private ChatBoxRepository chatBoxRepository;
    @Autowired
    private MessageRepository messageRepository;

    // Hiển thị giao diện chat chính
    @GetMapping("")
    public String showChatPage(Model model) {
        // Lấy thông tin người dùng từ Spring Security
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName(); // Tên người dùng

        // Lấy accountId từ UserService
        int accountId = chatService.getAccountIdFromUsername(username);

        model.addAttribute("accountId", accountId);
        // Lấy danh sách tất cả chatboxes
        List<chatBox> chatBoxes = chatService.getActiveChatBoxes();

        // Sắp xếp chatboxes theo thời gian tạo (mới nhất lên đầu)
        chatBoxes.sort((cb1, cb2) -> cb2.getCreateAt().compareTo(cb1.getCreateAt()));

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

    @MessageMapping("/sendMessage")
    @SendTo("/topic/messages")
    public message sendMessage(@Payload message message, SimpMessageHeaderAccessor headerAccessor) {
        // Kiểm tra nếu id = 0
        if (message.getId() == 0) {
            System.out.println("Processing message with ID = 0. Skipping duplicate checks.");
            return message; // Chấp nhận luôn tin nhắn
        }

        // Kiểm tra tin nhắn lặp bằng cách lưu ID tin nhắn cuối cùng trong session
        String lastMessageId = (String) headerAccessor.getSessionAttributes().get("lastMessageId");

        if (lastMessageId != null && lastMessageId.equals(String.valueOf(message.getId()))) {
            System.out.println("Duplicate message detected. Ignoring...");
            return null; // Bỏ qua nếu trùng
        }

        // Lưu lại ID tin nhắn vào session
        headerAccessor.getSessionAttributes().put("lastMessageId", String.valueOf(message.getId()));


        return message; // Trả về tin nhắn để gửi lại cho tất cả người subscribe
    }

    @PostMapping("/sendMessage")
    public ResponseEntity<message> sendMessage(@RequestParam("chatBoxId") int chatBoxId,
                                               @RequestParam("accountId") int accountId,
                                               @RequestParam("content") String content) {
        // Lưu tin nhắn vào cơ sở dữ liệu
        message savedMessage = chatService.saveMessage(chatBoxId, accountId, "shop", content);

        // Gửi tin nhắn đến các subscriber thông qua STOMP
        messagingTemplate.convertAndSend("/topic/messages", savedMessage);

        // Trả về thông tin tin nhắn đã lưu
        return ResponseEntity.ok(savedMessage);
    }

    // API để lấy danh sách tin nhắn theo chatbox
    @GetMapping("/messages/{chatBoxId}")
    @ResponseBody
    public List<message> getMessagesByChatBoxId(@PathVariable int chatBoxId) {
        return chatService.getMessagesByChatBoxId(chatBoxId);
    }

}




