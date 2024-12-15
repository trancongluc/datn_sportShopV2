package com.example.sportshopv2.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "\"User\"")
public class NguoiDung {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "code")
    private String code;
    private String full_name;
    private LocalDate date;
    @Column(name = "phone_number")
    private String phone_number;
    private String email;
    private String gender;
    private String image_file_name;
    @Column(name = "create_at")
    private LocalDateTime create_at;
    //    private String points;
    @Column(name = "create_by")
    private String create_by;

    @Column(name = "update_at")
    private LocalDateTime update_at;

    @Column(name = "update_by")
    private String update_by;
    @Column(name = "deleted")
    private Boolean deleted;

    @OneToMany(mappedBy = "khachHang", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JsonIgnore
    private List<Diachi> diachi = new ArrayList<>();
}
