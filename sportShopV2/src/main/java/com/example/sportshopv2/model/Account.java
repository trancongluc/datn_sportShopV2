package com.example.sportshopv2.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Data
@Entity
@Table(name = "Account")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user", nullable = false)
    @EqualsAndHashCode.Exclude // Loại bỏ vòng lặp trong hashCode() và equals()
    @JsonIgnore
    private User user;

    @Column(name = "username", nullable = false, unique = true, length = 50)
    private String username;

    @Column(name = "password", nullable = false, length = 255)
    private String password;

    @Column(name = "role", nullable = false, length = 50)
    private String role;

    @Column(name = "status", length = 50)
    private String status;

    @Column(name = "create_at", columnDefinition = "DATETIME DEFAULT GETDATE()")
    private Date createdAt = new Date();

    @Column(name = "create_by", length = 50)
    private String createdBy;

    @Column(name = "update_at")
    private Date updatedAt;

    @Column(name = "update_by", length = 50)
    private String updatedBy;

    @Column(name = "deleted", columnDefinition = "BIT DEFAULT 0")
    private boolean deleted = false;

}
