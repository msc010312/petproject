package com.example.demo.domain.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "pet_sitter")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(onlyExplicitlyIncluded = true)
public class SitterEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sitter_seq")
    @SequenceGenerator(name="sitter_seq", sequenceName = "sitter_seq", allocationSize = 1)
    private Long sitterId;

    @Column(name = "walk_price")
    private Long walkPrice;

    @Column(name = "hotel_price")
    private Long hotelPrice;

    @Column(name = "day_price")
    private Long dayPrice;

    @Lob
    private String presentation;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private UserEntity user;
}
