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
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
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

    private final int PAGE_SIZE = 2;


    @Override
    @Transactional
    public void makeReservation(ReservationRequestDto dto) {
        // 1. 시터, 오너 조회
        SitterEntity sitter = sitterRepository.findById(dto.getSitterId())
                .orElseThrow(() -> new RuntimeException("시터를 찾을 수 없습니다."));
        OwnerEntity owner = ownerRepository.findById(dto.getOwnerId())
                .orElseThrow(() -> new RuntimeException("오너를 찾을 수 없습니다."));

        String serviceType = dto.getServiceType().trim().toLowerCase();
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;

        // 2. 날짜 파싱

        ZonedDateTime zonedDateTime = ZonedDateTime.parse(dto.getStartDateTime(), formatter.withZone(ZoneId.of("Asia/Seoul")));
        LocalDateTime startDateTime = zonedDateTime.toLocalDateTime();
//        LocalDateTime startDateTime = LocalDateTime.parse(dto.getStartDateTime(), formatter);
        LocalDateTime endDateTime = null;
        if (dto.getEndDateTime() != null && !dto.getEndDateTime().isEmpty()) {
            endDateTime = LocalDateTime.parse(dto.getEndDateTime(), formatter);
        }

        // 3. 서비스별 가격 계산
        long price;
        switch (serviceType) {
            case "walk":
                price = Optional.ofNullable(sitter.getWalkPrice()).orElse(0L);
                break;
            case "daycare":
                price = Optional.ofNullable(sitter.getDayPrice()).orElse(0L);
                break;
            case "hotel":
                long days = (endDateTime != null) ? java.time.Duration.between(startDateTime, endDateTime).toDays() : 1;
                if (days <= 0) days = 1; // 최소 1일
                long dailyPrice = Optional.ofNullable(sitter.getHotelPrice()).orElse(0L);
                price = dailyPrice * days;
                break;
            default:
                throw new IllegalArgumentException("알 수 없는 서비스 타입입니다: " + serviceType);
        }

        // 4. 예약 엔티티 생성 및 저장
        ReserveEntity reserve = ReserveEntity.builder()
                .serviceType(serviceType)
                .location(dto.getLocation())
                .request(dto.getRequest())
                .date(startDateTime)
                .checkOut(endDateTime)
                .payment(price)
                .status("예약 확정")
                .sitter(sitter)
                .owner(owner)
                .build();
        reserveRepository.save(reserve);

        // 5. 결제 엔티티 저장
        PaymentEntity payment = PaymentEntity.builder()
                .paymentAmount((int) price)
                .paymentMethod(dto.getPaymentMethod())
                .transactionId(dto.getTransactionId())
                .paymentStatus("결제 완료")
                .paymentDate(LocalDateTime.now())
                .reservation(reserve)
                .build();
        paymentRepository.save(payment);

        // 6. 오너 총 결제 금액 누적 저장
        Long existingTotal = Optional.ofNullable(owner.getTotalPayment()).orElse(0L);
        owner.setTotalPayment(existingTotal + price);
        ownerRepository.save(owner);
    }

    @Override
    @Transactional
    public void updateReservationStatus() {
        // 현재 시간
        LocalDateTime now = LocalDateTime.now();

        // 예약이 진행 중인 상태에서 예약 시간이 지난 예약들 조회
        List<ReserveEntity> ongoingReservations = reserveRepository.findByStatusAndDateBefore("예약 확정", now);
        System.out.println("변경 대상 예약 수: " + ongoingReservations.size());

        // 예약 상태를 '완료'로 업데이트
        for (ReserveEntity reserve : ongoingReservations) {
            System.out.println("→ 예약 ID: " + reserve.getReserveId() + ", 현재 상태: " + reserve.getStatus() + ", 시작시간: " + reserve.getDate());
            reserve.setStatus("완료");
            reserveRepository.save(reserve);
        }
    }

    @Override
    public List<ReserveEntity> getAllReserve(int page) {
        int startRow = page * PAGE_SIZE + 1;
        int endRow = (page + 1) * PAGE_SIZE;
        return reserveRepository.ownerPage(startRow, endRow);
    }

    @Override
    public int getTotalCount() {
        return reserveRepository.countTotalreserve();
    }

    // @Scheduled 어노테이션을 사용하여 주기적으로 예약 상태를 업데이트
    @Scheduled(cron = "0 0/1 * * * *")  // 매 5분마다 실행
    @Transactional
    public void scheduledUpdateReservationStatus() {
        System.out.println("Scheduled update started at: " + LocalDateTime.now());
        updateReservationStatus();
    }
}
