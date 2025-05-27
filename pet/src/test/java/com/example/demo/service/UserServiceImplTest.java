package com.example.demo.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import com.example.demo.domain.entity.UserEntity;
import com.example.demo.domain.repository.UserRepository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Test
    @DisplayName("Email Duplication")
    void registerUser() {
        // Given
        UserEntity user = new UserEntity();
        user.setEmail("test@example.com");
        user.setPassword("password");

        given(userRepository.findByEmail("test@example.com")).willReturn(new UserEntity());

        // When & Then
        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            userService.registerUser(user);
        });

        assertEquals("이미 사용 중인 이메일입니다.", exception.getMessage());
        verify(userRepository, never()).save(any(UserEntity.class));
    }

}