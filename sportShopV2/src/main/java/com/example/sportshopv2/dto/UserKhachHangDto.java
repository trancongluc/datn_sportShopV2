package com.example.sportshopv2.dto;

import jakarta.persistence.Column;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserKhachHangDto {
    private Integer id;
    private String code;
    private String fullName;
    private String phoneNumber;
    private String email;
}
