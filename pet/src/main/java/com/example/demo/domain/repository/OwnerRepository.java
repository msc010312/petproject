package com.example.demo.domain.repository;

import com.example.demo.domain.entity.OwnerEntity;
import com.example.demo.domain.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OwnerRepository extends JpaRepository<OwnerEntity,Long> {
}
