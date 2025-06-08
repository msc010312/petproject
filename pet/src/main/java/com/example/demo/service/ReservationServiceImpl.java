package com.example.demo.service;

import com.example.demo.domain.dto.ReservationRequestDto;
import com.example.demo.domain.entity.OwnerEntity;
import com.example.demo.domain.entity.PaymentEntity;
import com.example.demo.domain.entity.ReserveEntity;
import com.example.demo.domain.entity.SitterEntity;
import com.example.demo.domain.repository.OwnerRepository;
import com.example.demo.domain.repository.PaymentRepository;
import com.example.demo.domain.repository.ReserveRepository;
import com.example.demo.domain.repository.SitterRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor

public class ReservationServiceImpl implements ReservationService {

    @Autowired
    private SitterRepository sitterRepository;

    @Autowired
    private OwnerRepository ownerRepository;

    @Autowired
    private ReserveRepository reserveRepository;

    @Autowired
    private PaymentRepository paymentRepository;


    @Override
    @Transactional
    public void makeReservation(ReservationRequestDto dto) {
        SitterEntity sitter = sitterRepository.findById(dto.getSitterId())
                .orElseThrow(() -> new RuntimeException("시터를 찾을 수 없습니다."));

        OwnerEntity owner = ownerRepository.findById(dto.getOwnerId())
                .orElseThrow(() -> new RuntimeException("오너를 찾을 수 없습니다."));

        long price = switch (dto.getServiceType()) {
            case "walk" -> sitter.getWalkPrice();
            case "hotel" -> sitter.getHotelPrice();
            case "short" -> sitter.getDayPrice();
            default -> throw new IllegalArgumentException("알 수 없는 서비스 타입");
        };

        ReserveEntity reserve = ReserveEntity.builder()
                .serviceType(dto.getServiceType())
                .location(dto.getLocation())
                .request(dto.getRequest())
                .date(LocalDateTime.parse(dto.getDateTime()))
                .payment(price)
                .status("예약 확정")
                .sitter(sitter)
                .owner(owner)
                .build();
        reserveRepository.save(reserve);

        PaymentEntity payment = PaymentEntity.builder()
                .paymentAmount((int) price)
                .paymentMethod(dto.getPaymentMethod())
                .transactionId(dto.getTransactionId())
                .paymentStatus("결제 완료")
                .paymentDate(LocalDateTime.now())
                .reservation(reserve)
                .build();
        paymentRepository.save(payment);
    }
}
