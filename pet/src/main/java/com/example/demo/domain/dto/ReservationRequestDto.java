package com.example.demo.domain.dto;

import lombok.Data;

@Data

public class ReservationRequestDto {
    private Long sitterId;
    private Long ownerId;
    private String serviceType;
    private String location;
    private String request;
    private String startDateTime;
    private String endDateTime;
    private String paymentMethod;
    private String transactionId;
}
