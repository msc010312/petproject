package com.example.demo.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "reservation")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class ReserveEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "reserve_seq")
    @SequenceGenerator(name="reserve_seq", sequenceName = "reserve_seq", allocationSize = 1)
    private Long reserveId;

    @Column(name = "service_type", nullable = false)
    private String serviceType;

    @Column(name = "location", nullable = false)
    private String location;

    @Column(name = "reservation_date", nullable = false)
    private LocalDateTime date;

    @Lob
    @Column(name = "request", nullable = false)
    private String request;

    @Column(name = "payment", nullable = false)
    private Long payment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sitter_id")
    private SitterEntity sitter;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private OwnerEntity owner;

    @Column(name = "status", nullable = false)
    private String status;
}
