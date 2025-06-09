package com.example.demo.service;

import com.example.demo.config.auth.PrincipalDetail;
import com.example.demo.domain.dto.UserDto;
import com.example.demo.domain.entity.UserEntity;
import com.example.demo.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByEmail(email);
        if (userEntity == null) {
            throw new UsernameNotFoundException("사용자를 찾을 수 없습니다.");
        }

        UserDto userDto = UserDto.builder()
                .email(userEntity.getEmail())
                .password(userEntity.getPassword())
                .name(userEntity.getName())
                .role(userEntity.getRole())
                .phone(userEntity.getPhone())
                .createAt(LocalDateTime.now())
                .build();

        return new PrincipalDetail(userDto);  // PrincipalDetail로 리턴
    }
}
