package com.example.demo.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "pet")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class PetEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pet_seq")
    @SequenceGenerator(name="pet_seq", sequenceName = "pet_seq", allocationSize = 1)
    private Long petId;

    @Column(name = "pet_name")
    private String petName;

    @Column(name = "pet_kind")
    private String petKind;

    @Column(name = "pet_age")
    private Long petAge;

    @Lob
    @Column(name = "pet_char")
    private String petChar;

    @Lob
    private String caution;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private OwnerEntity owner;
}
