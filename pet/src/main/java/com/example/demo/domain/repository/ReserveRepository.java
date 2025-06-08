package com.example.demo.domain.repository;

import com.example.demo.domain.entity.ReserveEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReserveRepository extends JpaRepository<ReserveEntity, Long> {
}
