package com.example.demo.service;

import com.example.demo.domain.entity.UserEntity;
import com.example.demo.domain.repository.UserRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Data

public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserEntity saveUser(UserEntity user) {
        return userRepository.save(user);
    }

    // 회원가입 처리
    @Override
    public UserEntity registerUser(UserEntity user) {
        return userRepository.save(user);
    }

}
