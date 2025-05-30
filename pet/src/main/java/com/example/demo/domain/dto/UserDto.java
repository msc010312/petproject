package com.example.demo.domain.dto;

import com.example.demo.domain.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {
    private Long userId;
    private String email;
    private String password;
    private String userPwRe;
    private String name;
    private String address;
    private String phone;
    private String role;
    private LocalDateTime createAt;


    //OAUTH2 CLIENT INFO
    private String provider;
    private String providerId;

    // DTO -> Entity 변환
    public UserEntity toEntity() {
        return UserEntity.builder()
                .userId(this.userId)
                .email(this.email)
                .password(this.password)
                .name(this.name)
                .address(this.address)
                .phone(this.phone)
                .role(this.role)
                .provider(this.provider)
                .createdAt(this.createAt)
                .build();
    }

    // Entity -> DTO 변환
    public static UserDto toDto(UserEntity entity) {
        return UserDto.builder()
                .userId(entity.getUserId())
                .email(entity.getEmail())
                .password(entity.getPassword())
                .name(entity.getName())
                .address(entity.getAddress())
                .phone(entity.getPhone())
                .role(entity.getRole())
                .provider(entity.getProvider())
                .createAt(entity.getCreatedAt())
                .build();
    }

}
