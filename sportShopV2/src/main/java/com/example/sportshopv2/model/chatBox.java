package com.example.sportshopv2.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "ChatBox")
public class chatBox {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String name;

    @Column(name = "create_at")
    private LocalDateTime createAt = LocalDateTime.now();

    @Column(name = "create_by", nullable = false)
    private int createBy;

    @Column(name = "update_at")
    private LocalDateTime updateAt;

    @Column(name = "update_by")
    private Integer updateBy;

    @Column
    private boolean deleted = false;
    @JsonBackReference
    @OneToMany(mappedBy = "chatBox", cascade = CascadeType.ALL)
    private List<message> messages = new ArrayList<>();


    public chatBox(int chatBoxId) {
    }
}
