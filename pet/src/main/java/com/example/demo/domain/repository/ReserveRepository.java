package com.example.demo.domain.repository;

import com.example.demo.domain.entity.OwnerEntity;
import com.example.demo.domain.entity.ReserveEntity;
import com.example.demo.domain.entity.SitterEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ReserveRepository extends JpaRepository<ReserveEntity, Long> {
    List<ReserveEntity> findByOwner(OwnerEntity owner);

    List<ReserveEntity> findBySitter(SitterEntity sitter);

    List<ReserveEntity> findByStatusAndDateBefore(String status, LocalDateTime date);
}
