package com.example.sportshopv2.entity;


import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Account")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user", nullable = false)
    @EqualsAndHashCode.Exclude // Loại bỏ vòng lặp trong hashCode() và equals()
    private User user;

    @Column(name = "username", nullable = false, unique = true, length = 50)
    private String username;

    @Column(name = "password", nullable = false, length = 255)
    private String password;

    @Column(name = "role", nullable = false, length = 50)
    private String role;

    @Column(name = "status", length = 50)
    private String status;

    @Column(name = "created_at", columnDefinition = "DATETIME DEFAULT GETDATE()")
    private Date createdAt = new Date();

    @Column(name = "created_by", length = 50)
    private String createdBy;

    @Column(name = "updated_at")
    private Date updatedAt;

    @Column(name = "updated_by", length = 50)
    private String updatedBy;

    @Column(name = "deleted", columnDefinition = "BIT DEFAULT 0")
    private boolean deleted = false;
}
