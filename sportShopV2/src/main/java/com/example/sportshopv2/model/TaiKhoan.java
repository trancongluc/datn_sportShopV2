package com.example.sportshopv2.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Account")
public class TaiKhoan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "id_user", nullable = false)
    private NguoiDung nguoiDung;

    private String username;
    private String role;
    private String password;
    private String status;
    private LocalDateTime create_at;
    private String create_by;
    private LocalDateTime update_at;
    private String update_by;
    private Boolean deleted;
}
