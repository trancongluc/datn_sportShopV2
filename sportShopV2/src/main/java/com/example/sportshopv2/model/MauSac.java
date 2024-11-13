package com.example.sportshopv2.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Color")
public class MauSac extends BaseEntity{
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "name")
    private String tenMauSac;
    @Column(name = "status")
    private String trangThai;

    public MauSac(Integer id) {
        this.id = id;
    }
}
