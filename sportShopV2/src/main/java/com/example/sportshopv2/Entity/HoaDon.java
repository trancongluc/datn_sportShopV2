package com.example.sportshopv2.Entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Bill")
public class HoaDon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "id_account")
    private String id_account;

    @NotBlank(message = "{message.username}")
    @Column(name = "user_name")
    private String user_name;

    @NotBlank(message = "{message.phonenumber}")
    @Column(name = "phone_number")
    private String phone_number;

    @NotNull(message = "{message.totalmoney}")
    @Column(name = "total_money")
    private Float total_money;

    @NotBlank(message = "{message.status}")
    @Column(name = "status")
    private String status;
    @Column(name="address")

    private String address;

    @Column(name = "ship_date")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ssss")
    private String ship_date;

    @Column(name = "receive_date")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ssss")
    private String receive_date;

    @Column(name = "confirmation_date")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ssss")
    private String confirmation_date;

    @Column(name = "desire_date")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ssss")
    private String desire_date;
    @Column(name = "value_point")

    private Integer value_point;
    @Column(name = "point_use")
    private Integer point_use;

    @Column(name = "money_ship")
    private Float money_ship;

    @Column(name = "pay_method")
    private String pay_method;

    @Column(name = "pay_status")
    private String pay_status;

    @Column(name = "bill_code")
    private String bill_code;

    @Column(name = "transaction_date")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ssss")
    private String transaction_date;
    @Column(name = "email")
    private String email;
    @Column(name = "type")
    private String type;
    @Column(name = "note")
    private String note;

    @Column(name = "create_at")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ssss")
    private String create_at;

    @Column(name = "create_by")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ssss")
    private String create_by;

    @Column(name = "update_at")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String update_at;

    @Column(name = "update_by")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String update_by;
    @Column(name = "deleted")
    private Boolean deleted;
}
