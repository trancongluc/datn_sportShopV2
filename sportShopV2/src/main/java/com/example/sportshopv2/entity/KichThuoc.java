package com.example.sportshopv2.entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Size")
public class KichThuoc extends BaseEntity{
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "name")
    private String tenKichThuoc;
    @Column(name = "status")
    private String trangThai;

    public KichThuoc(Integer id) {
        this.id = id;
    }
}
