package com.example.sportshopv2.dto;

import lombok.*;

import java.time.LocalDateTime;

public class MessageDTO {
    private int chatBoxId; // Chỉ nhận ID thay vì đối tượng
    private int accountId;
    private String role;
    private String content;
    private String status;
    private LocalDateTime timestamp;
    private Integer updateBy;
    private LocalDateTime updateAt;

    // Getters và Setters
}

