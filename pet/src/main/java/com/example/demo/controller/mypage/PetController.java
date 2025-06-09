package com.example.demo.controller.mypage;

import com.example.demo.domain.dto.PetDto;
import com.example.demo.domain.entity.PetEntity;
import com.example.demo.service.PetServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor

public class PetController {

    @Autowired
    private PetServiceImpl petService;

    @PostMapping("/pet")
    public ResponseEntity<?> savePet(@RequestBody PetDto petDto) {
        PetEntity saved = petService.savePet(petDto);
        PetDto result = PetDto.builder()
                .petId(saved.getPetId())
                .petName(saved.getPetName())
                .petKind(saved.getPetKind())
                .petAge(saved.getPetAge())
                .petChar(saved.getPetChar())
                .caution(saved.getCaution())
                .build();
        return ResponseEntity.ok().body(result);
    }

    @DeleteMapping("/pet/{id}")
    public ResponseEntity<?> deletePet(@PathVariable Long id) {
        petService.deletePet(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/pet/list/{ownerId}")
    public ResponseEntity<List<PetDto>> getPetsByOwnerId(@PathVariable Long ownerId) {
        List<PetDto> petList = petService.findPetsByOwnerId(ownerId);
        return ResponseEntity.ok(petList);
    }

}
