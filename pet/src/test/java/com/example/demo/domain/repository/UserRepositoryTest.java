package com.example.demo.domain.repository;

import com.example.demo.domain.entity.UserEntity;
import com.example.demo.ennotion.UserType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Test
    public void test() throws Exception{
        UserEntity user = UserEntity.builder()
                .email("aaa@test.com")
                .password("1234")
                .name("max")
                .address("aaa-bbbb")
                .phone("010-1111-1111")
                .userType(UserType.Owner)
                .build();
        userRepository.save(user);
    }
}