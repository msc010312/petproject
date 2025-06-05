package com.example.demo.domain.repository;

import com.example.demo.domain.entity.SitterEntity;
import com.example.demo.domain.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SitterRepository extends JpaRepository<SitterEntity,Long> {
}
