package com.example.demo.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "pet_payment")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pay_seq")
    @SequenceGenerator(name="pay_seq", sequenceName = "pay_seq", allocationSize = 1)
    @Column(name = "pay_id")
    private Long id;

    @Column(name="payment_method")
    private String paymentMethod;

    @Column(name = "transaction_id")
    private String transactionId;  // 이니시스 거래 ID (tid)

    @Column(name = "payment_status")
    private String paymentStatus;

    @Column(name = "payment_amount")
    private Integer paymentAmount;

    @Column(name = "payment_date")
    private LocalDateTime paymentDate;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "reservation_id")
    private ReserveEntity reservation;
}