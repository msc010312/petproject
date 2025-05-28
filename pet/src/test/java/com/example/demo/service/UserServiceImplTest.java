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
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Test
    @DisplayName("이미 등록된 이메일로 회원가입 시 예외 발생")
    void registerUser_Fail() {
        // Given
        UserEntity user = new UserEntity();
        user.setEmail("test@example.com");
        user.setPassword("password");

        given(userRepository.findByEmail("test@example.com")).willReturn(new UserEntity());

        // When & Then
        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            try {
                userService.registerUser(user);
            } catch (IllegalStateException e) {
                // 콘솔 출력
                System.out.println("예외 발생 메시지 : " + e.getMessage());
                throw e; // 다시 던져서 assertThrows가 인식하도록
            }
        });

        assertEquals("이미 사용 중인 이메일입니다.", exception.getMessage());
        verify(userRepository, never()).save(any(UserEntity.class));
    }

}