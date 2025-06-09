package com.example.demo.domain.repository;

import com.example.demo.domain.entity.SitterEntity;
import com.example.demo.domain.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface SitterRepository extends JpaRepository<SitterEntity,Long> {
    SitterEntity findByUser(UserEntity user);

    Optional<SitterEntity> findByUser_Email(String email);
}
