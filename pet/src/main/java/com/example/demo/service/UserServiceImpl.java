package com.example.demo.service;

import com.example.demo.domain.entity.OwnerEntity;
import com.example.demo.domain.entity.SitterEntity;
import com.example.demo.domain.entity.UserEntity;
import com.example.demo.domain.repository.OwnerRepository;
import com.example.demo.domain.repository.SitterRepository;
import com.example.demo.domain.repository.UserRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Data
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OwnerRepository ownerRepository;

    @Autowired
    private SitterRepository sitterRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserEntity saveUser(UserEntity user) {
        return userRepository.save(user);
    }

    // 회원가입 처리
    @Override
    public UserEntity registerUser(UserEntity user) {
        if (userRepository.findByEmail(user.getEmail()) != null) {
            throw new IllegalStateException("이미 사용 중인 이메일입니다.");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setProvider("Local");

        UserEntity savedUser = userRepository.save(user);

        if ("ROLE_OWNER".equals(user.getRole())) {
            OwnerEntity owner = OwnerEntity.builder()
                    .user(savedUser)
                    .build();
            ownerRepository.save(owner);
        } else if ("ROLE_SITTER".equals(user.getRole())) {
            SitterEntity sitter = SitterEntity.builder()
                    .user(savedUser)
                    .build();
            sitterRepository.save(sitter);
        }
        return savedUser;
    }

    @Override
    public UserEntity findByEmail(String email) {
        return userRepository.findByEmail((email));
    }

//        if (userRepository.findByEmail(get)) {
//            throw new DuplicateEmailException("이미 사용 중인 이메일입니다.");
//        return userRepository.findByEmail(email); // 이메일로 사용자 조회


    @Override
    public void deleteById(Long userId) {
        userRepository.deleteById(userId);
    }
}
