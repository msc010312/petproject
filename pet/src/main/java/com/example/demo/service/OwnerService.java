package com.example.demo.service;

import com.example.demo.domain.entity.OwnerEntity;

public interface OwnerService {
    OwnerEntity findByUserEmail(String email);
}
