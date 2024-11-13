package com.example.sportshopv2.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Category")
public class TheLoai extends BaseEntity{
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "name")
    private String tenTheLoai;
    @Column(name = "status")
    private String trangThai;

    public TheLoai(Integer id) {
        this.id = id;
    }
}
