package com.example.demo.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@DynamicUpdate
@Table(name = "pet_owner")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class OwnerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "owner_seq")
    @SequenceGenerator(name="owner_seq", sequenceName = "owner_seq", allocationSize = 1)
    private Long ownerId;

    @Column(name = "total_payment")
    private Long totalPayment = 0L;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;
}
