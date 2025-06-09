package com.example.demo.service;

import com.example.demo.domain.entity.SitterEntity;
import com.example.demo.domain.repository.SitterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor

public class SitterServiceImpl implements SitterService {

    @Autowired
    SitterRepository sitterRepository;

    @Override
    public List<SitterEntity> getAllSitters() {
        return sitterRepository.findAll();
    }
}
