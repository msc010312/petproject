package com.example.demo.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class SitterForm {
    private String name;
    private String address;
    private String phone;

    private Long walkPrice;
    private Long hotelPrice;
    private Long dayPrice;

    private String presentation;
}
