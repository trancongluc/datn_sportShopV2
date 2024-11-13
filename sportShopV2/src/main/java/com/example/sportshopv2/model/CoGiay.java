package com.example.sportshopv2.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Collar")
public class CoGiay extends BaseEntity{
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "name")
    private String tenCoGiay;
    @Column(name = "status")
    private String trangThai;

    public CoGiay(Integer id) {
        this.id = id;
    }
}
