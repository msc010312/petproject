package com.example.demo.service;

import com.example.demo.domain.dto.ReservationRequestDto;

public interface ReservationService {
    void makeReservation(ReservationRequestDto dto);

    void updateReservationStatus();
}
