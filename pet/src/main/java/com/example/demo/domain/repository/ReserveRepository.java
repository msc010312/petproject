package com.example.demo.domain.repository;

import com.example.demo.domain.entity.OwnerEntity;
import com.example.demo.domain.entity.ReserveEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReserveRepository extends JpaRepository<ReserveEntity, Long> {
    List<ReserveEntity> findByOwner(OwnerEntity owner);
}
