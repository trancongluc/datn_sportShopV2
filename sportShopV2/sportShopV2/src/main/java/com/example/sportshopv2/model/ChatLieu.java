package com.example.sportshopv2.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Material")
public class ChatLieu extends BaseEntity{
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "name")
    private String tenChatLieu;
    @Column(name = "status")
    private String trangThai;

    public ChatLieu(Integer id) {
        this.id = id;
    }
}
