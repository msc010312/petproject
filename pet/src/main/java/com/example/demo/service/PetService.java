package com.example.demo.service;

import com.example.demo.domain.dto.PetDto;
import com.example.demo.domain.entity.PetEntity;

import java.util.List;

public interface PetService {
    PetEntity savePet(PetDto dto);

    void deletePet(Long id);

    List<PetDto> findPetsByOwnerId(Long ownerId);
}
