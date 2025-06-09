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
import java.time.format.DateTimeFormatter;
import java.util.Optional;

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

        System.out.println("=== Sitter 정보 확인 ===");
        System.out.println("Sitter ID: " + sitter.getSitterId());
        System.out.println("WalkPrice: " + sitter.getWalkPrice());
        System.out.println("HotelPrice: " + sitter.getHotelPrice());
        System.out.println("DayPrice: " + sitter.getDayPrice());


        String serviceType = dto.getServiceType().trim().toLowerCase(); // 공백 제거 + 소문자화

        long price = switch (serviceType) {
            case "walk" -> Optional.ofNullable(sitter.getWalkPrice()).orElse(0L);
            case "hotel" -> Optional.ofNullable(sitter.getHotelPrice()).orElse(0L);
            case "short" -> Optional.ofNullable(sitter.getDayPrice()).orElse(0L);
            default -> throw new IllegalArgumentException("알 수 없는 서비스 타입");
        };

        System.out.println("sitter.getDayPrice(): " + sitter.getDayPrice());
        System.out.println("sitter.getHotelPrice(): " + sitter.getHotelPrice());
        System.out.println("sitter.getWalkPrice(): " + sitter.getWalkPrice());


        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
        LocalDateTime dateTime = LocalDateTime.parse(dto.getDateTime(), formatter);

        ReserveEntity reserve = ReserveEntity.builder()
                .serviceType(dto.getServiceType())
                .location(dto.getLocation())
                .request(dto.getRequest())
                .date(dateTime)
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

        System.out.println("결제 정보 저장 직전");
        System.out.println("Amount: " + payment.getPaymentAmount());
        System.out.println("Transaction ID: " + payment.getTransactionId());
        System.out.println("예약 ID: " + reserve.getReserveId());

        paymentRepository.save(payment);

        Long existingTotal = owner.getTotalPayment() != null ? owner.getTotalPayment() : 0L;
        Long updatedTotal = existingTotal + price;
        owner.setTotalPayment(updatedTotal);
        ownerRepository.save(owner);
        ownerRepository.flush();
    }
}
