package com.example.demo.service;

import com.example.demo.domain.entity.OwnerEntity;
import com.example.demo.domain.repository.OwnerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor

public class OwnerServiceImpl implements OwnerService {

    @Autowired
    private OwnerRepository ownerRepository;

    @Override
    public OwnerEntity findByUserEmail(String email) {
        return ownerRepository.findByUser_Email(email)
                .orElseThrow(() -> new RuntimeException("해당 이메일로 오너를 찾을 수 없습니다."));
    }
}
