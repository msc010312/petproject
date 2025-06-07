package com.example.demo.service;

import com.example.demo.domain.entity.UserEntity;

import java.util.List;

public interface UserService {

    UserEntity saveUser(UserEntity user);

    // 회원가입 처리
    UserEntity registerUser(UserEntity user);

    //로그인
    UserEntity findByEmail(String email);

    UserEntity findById(Long userId);

    //회원탈퇴
    void deleteById(Long userId);
}
