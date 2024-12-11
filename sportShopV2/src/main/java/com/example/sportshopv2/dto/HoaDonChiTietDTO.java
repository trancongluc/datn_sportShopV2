package com.example.sportshopv2.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HoaDonChiTietDTO {
    private String fullName;
    private String phoneNumber;
    private String tinh;
    private Date shipDate;
    private Date receiveDate;
    private BigDecimal totalMoney;
    private BigDecimal moneyShip;
    private String status;
    private Integer pointUse;
    private Integer quantity;
    private String tenAnh;
    private String tenMauSac;


    }