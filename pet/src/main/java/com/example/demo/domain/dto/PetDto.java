package com.example.demo.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class PetDto {
    private Long petId;
    private String petName;
    private String petKind;
    private Long petAge;
    private String petChar;
    private String caution;
    private Long ownerId;
}
