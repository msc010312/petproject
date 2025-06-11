package com.example.demo.service;

import com.example.demo.domain.dto.ReservationRequestDto;
import com.example.demo.domain.entity.ReserveEntity;

import java.util.List;

public interface ReservationService {
    void makeReservation(ReservationRequestDto dto);

    void updateReservationStatus();

    List<ReserveEntity> getAllReserve(int page);
    public int getTotalCount();
}
