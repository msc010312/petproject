package com.example.demo.domain.dto;

import lombok.Data;

@Data

public class ReservationRequestDto {
    private Long sitterId;
    private Long ownerId;
    private String serviceType;
    private String location;
    private String request;
    private String dateTime;
    private String paymentMethod;
    private String transactionId;
}
