package com.example.demo.service;

import com.example.demo.domain.dto.PetDto;
import com.example.demo.domain.entity.OwnerEntity;
import com.example.demo.domain.entity.PetEntity;
import com.example.demo.domain.repository.OwnerRepository;
import com.example.demo.domain.repository.PetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor

public class PetServiceImpl implements PetService {

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private OwnerRepository ownerRepository;

    @Override
    public PetEntity savePet(PetDto dto) {
        OwnerEntity owner = ownerRepository.findById(dto.getOwnerId())
                .orElseThrow(() -> new IllegalArgumentException("잘못된 ownerId"));

        PetEntity pet = PetEntity.builder()
                .petName(dto.getPetName())
                .petKind(dto.getPetKind())
                .petAge(dto.getPetAge())
                .petChar(dto.getPetChar())
                .caution(dto.getCaution())
                .owner(owner)
                .build();

        return petRepository.save(pet);
    }

    @Override
    public void deletePet(Long id) {
        petRepository.deleteById(id);
    }

    @Override
    public List<PetDto> findPetsByOwnerId(Long ownerId) {
        OwnerEntity owner = ownerRepository.findById(ownerId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 오너 ID"));

        return petRepository.findByOwner(owner).stream()
                .map(pet -> PetDto.builder()
                        .petId(pet.getPetId())
                        .petName(pet.getPetName())
                        .petKind(pet.getPetKind())
                        .petAge(pet.getPetAge())
                        .petChar(pet.getPetChar())
                        .caution(pet.getCaution())
                        .build())
                .collect(Collectors.toList());
    }
}
