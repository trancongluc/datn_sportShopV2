package com.example.sportshopv2.dto;

import lombok.*;

@Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    @Builder
public class UserDTO {
    private Integer id;
    private String code;
    private String fullName;
    private String phoneNumber;
    private String email;
    private String gender;
    private String date;
    private int points;

    private String imageFileName;
}
