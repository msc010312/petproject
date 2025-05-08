package com.example.demo.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "allUser")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserEntity {
    //    USER_ID NUMBER PRIMARY KEY,
//    EMAIL VARCHAR2(100) UNIQUE NOT NULL,
//    PASSWORD VARCHAR2(255) NOT NULL,
//    NAME VARCHAR2(100) NOT NULL,
//    ADDRESS VARCHAR2(200),
//    PHONE VARCHAR2(20),
//    USER_TYPE VARCHAR2(10) CHECK (USER_TYPE IN ('OWNER', 'SITTER')),
//    CREATED_AT TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "all_user_seq")
    @SequenceGenerator(name = "all_user_seq", sequenceName = "all_user_seq", allocationSize = 1)
    private Long userId;

    @Column(name = "email", nullable = false, unique = true, length = 100)
    private String email;

    @Column(name = "password", nullable = false, length = 255)
    private String password;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "address", length = 200)
    private String address;

    @Column(name = "phone", length = 20)
    private String phone;

    @Column(name = "usertype", length = 10)
    private String userType;

    @Column(name = "CREATED_AT")
    private LocalDateTime createdAt = LocalDateTime.now();

    @PrePersist
    public void prePersist() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
    }

}
