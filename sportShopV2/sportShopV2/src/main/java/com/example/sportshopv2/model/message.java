package com.example.sportshopv2.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name="Message")
public class message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "id_chatbox", nullable = false)
    private chatBox chatBox;

    @Column(name = "id_account", nullable = false)
    private int accountId;

    @Column
    private String role;

    @Column
    private String status;

    @Column(nullable = false)
    private String content;

    @Column
    private LocalDateTime timestamp = LocalDateTime.now();

    @Column(name = "update_by")
    private Integer updateBy;

    @Column(name = "update_at")
    private LocalDateTime updateAt;
}
