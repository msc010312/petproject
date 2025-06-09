package com.example.demo.controller.reserve;

import com.example.demo.domain.dto.ReservationRequestDto;
import com.example.demo.service.ReservationServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reserve")
@RequiredArgsConstructor
@Slf4j

public class ReserveApiController {

    @Autowired
    ReservationServiceImpl reservationService;

    @PostMapping
    public ResponseEntity<?> createReservation(@RequestBody ReservationRequestDto dto) {
        log.info("[POST] /api/reserve 호출됨, DTO: {}", dto);
        try {
            reservationService.makeReservation(dto);
            return ResponseEntity.ok().body("예약 및 결제가 완료되었습니다.");
        } catch (Exception e) {
            log.error("예약 처리 중 오류 발생: {}", e.getMessage());
            return ResponseEntity.badRequest().body("예약 처리 중 오류 발생: " + e.getMessage());
        }
    }

}
