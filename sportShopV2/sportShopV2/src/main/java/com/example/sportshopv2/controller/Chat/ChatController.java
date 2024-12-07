package com.example.sportshopv2.controller.Chat;


import com.example.sportshopv2.model.chatBox;
import com.example.sportshopv2.model.message;
import com.example.sportshopv2.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping
public class ChatController {

    //  @Autowired
    //   private ChatMessageRepository chatMessageRepository;
    @Autowired
    private ChatService chatService;

//    @MessageMapping("/sendMessage") // Gửi tin từ client (khách hàng)
//    @SendTo("/topic/messages")
//    public String handleClientMessage(String message) {
//        saveMessage("client", "shop", message);
//        return "Client: " + message;
//    }
//
//    @MessageMapping("/sendToClient") // Gửi tin từ shop
//    @SendTo("/topic/messages")
//    public String handleShopMessage(String message) {
//        saveMessage("shop", "client", message);
//        return "Shop: " + message;
//    }


    //Update moi
//    @MessageMapping("/sendMessage")
//    @SendTo("/topic/messages")
//    public String handleClientMessage(String message) {
//        saveMessage("client", "shop", message);
//        return "Client: " + message;
//    }
//
//    @MessageMapping("/sendToClient")
//    @SendTo("/topic/messages")
//    public String handleShopMessage(String message) {
//        saveMessage("shop", "client", message);
//        return "Shop: " + message;
//    }
//
//    private void saveMessage(String sender, String recipient, String content) {
//        ChatMessage chatMessage = new ChatMessage();
//        chatMessage.setSender(sender);
//        chatMessage.setRecipient(recipient);
//        chatMessage.setContent(content);
//        chatMessage.setTimestamp(LocalDateTime.now());
//        chatMessageRepository.save(chatMessage);
//    }


    @MessageMapping("/chat") // Nhận tin nhắn từ client
    @SendTo("/topic/messages") // Phát tin nhắn đến các client đã subscribe
    public com.example.sportshopv2.model.message handleChatMessage(message cMessage) {
        // Lưu tin nhắn vào database
        message mess = chatService.saveMessage(
                cMessage.getChatBox().getId(),
                cMessage.getAccountId(),
                cMessage.getRole(),
                cMessage.getContent()
        );
        return mess; // Phát tin nhắn tới các client
    }

    @GetMapping("/chatboxes")
    @ResponseBody
    public List<chatBox> getActiveChatBoxes() {
        return chatService.getActiveChatBoxes();
    }

    @GetMapping("/messages/{chatBoxId}")
    @ResponseBody
    public List<message> getMessages(@PathVariable int chatBoxId) {
        return chatService.getMessagesByChatBoxId(chatBoxId);
    }
}
