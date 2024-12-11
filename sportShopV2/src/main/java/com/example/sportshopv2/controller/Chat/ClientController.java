//package com.example.sportshopv2.controller.Chat;
//
//import com.example.sportshopv2.model.chatBox;
//import com.example.sportshopv2.model.message;
//import com.example.sportshopv2.repository.ChatBoxRepository;
//import com.example.sportshopv2.repository.MessageRepository;
//import com.example.sportshopv2.service.ChatService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.messaging.handler.annotation.MessageMapping;
//import org.springframework.messaging.handler.annotation.Payload;
//import org.springframework.messaging.handler.annotation.SendTo;
//import org.springframework.messaging.simp.SimpMessagingTemplate;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//import java.util.Optional;
//@Controller
//@RequestMapping("/chat")
//public class ClientController {
//
//    @Autowired
//    private ChatService chatService;
//    @Autowired
//    private SimpMessagingTemplate messagingTemplate;
//    @Autowired
//    private ChatBoxRepository chatBoxRepository;
//    @Autowired
//    private MessageRepository messageRepository;
//
//    // Hiển thị giao diện chat chính
//    @GetMapping("")
//    public String showChatPage(Model model) {
//        // Lấy thông tin người dùng từ Spring Security
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String username = authentication.getName(); // Tên người dùng
//
//        // Lấy accountId từ UserService
//        int accountId = chatService.getAccountIdFromUsername(username);
//
//        model.addAttribute("accountId", accountId);
//        // Lấy danh sách tất cả chatboxes
//        // Lấy chatBox theo ID
//        Optional<chatBox> chatBox = chatService.getChatBoxById(1004);
//
//        if (chatBox.isEmpty()) {
//            // Nếu không tìm thấy chatBox, có thể return 404 hoặc trang lỗi.
//            model.addAttribute("error", "ChatBox không tồn tại!");
//            return "error";
//        }
//
//        // Lấy danh sách tin nhắn của chatBox
//        List<message> messages = chatService.getMessagesByChatBoxId(1004);
//
//        // Thêm chatBox và tin nhắn vào Model để gửi ra view
//        model.addAttribute("chatBox", chatBox.get());
//        model.addAttribute("messages", messages);
//
//        return "MuaHang/clientChat";
//    }
//
//    @MessageMapping("client/sendMessage")
//    @SendTo("client/topic/messages")
//    public message sendMessage(@Payload message message) {
//        //Log test tin nhắn nhận được phía Client
//        System.out.println("Received message: " + message);
//        // Lưu tin nhắn vào cơ sở dữ liệu hoặc xử lý thêm
//        return message; // Trả về tin nhắn để gửi lại cho tất cả người subscribe
//    }
//
//    @PostMapping("/sendMessage")
//    public ResponseEntity<message> sendMessage(@RequestParam("chatBoxId") int chatBoxId,
//                                               @RequestParam("accountId") int accountId,
//                                               @RequestParam("content") String content) {
//        // Lưu tin nhắn vào cơ sở dữ liệu
//        message savedMessage = chatService.saveMessage(chatBoxId, accountId, "client", content);
//
//        // Gửi tin nhắn đến các subscriber thông qua STOMP
//        messagingTemplate.convertAndSend("/topic/messages", savedMessage);
//
//        // Trả về thông tin tin nhắn đã lưu
//        return ResponseEntity.ok(savedMessage);
//    }
//
//    // API để lấy danh sách tin nhắn theo chatbox
//    @GetMapping("/messages/{chatBoxId}")
//    @ResponseBody
//    public List<message> getMessagesByChatBoxId(@PathVariable int chatBoxId) {
//        return chatService.getMessagesByChatBoxId(chatBoxId);
//    }
//
//}
