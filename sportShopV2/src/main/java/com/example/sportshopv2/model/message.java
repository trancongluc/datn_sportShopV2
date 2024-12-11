package com.example.sportshopv2.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Entity
@Table(name = "Message")
public class message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
   // @JsonManagedReference
    @ManyToOne(cascade = CascadeType.PERSIST)  // Hoặc CascadeType.ALL nếu muốn lưu cả chatBox khi lưu message
    @JoinColumn(name = "id_chatbox", nullable = false)
    private chatBox chatBox;

    @Column(name = "id_account", nullable = false)
    private int accountId;

    @Column(nullable = false)
    private String role;
    @Column(name = "status")
    private String status;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private LocalDateTime timestamp;

    @Column(name = "update_by")
    private Integer updateBy;

    @Column(name = "update_at")
    private LocalDateTime updateAt;

    // Đảm bảo timestamp được thiết lập tự động khi tạo mới
    @PrePersist
    protected void onCreate() {
        this.timestamp = LocalDateTime.now();
    }


}
