package com.example.demo.domain.repository;

import com.example.demo.domain.entity.UserEntity;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Test
    public void test() throws Exception{
        // given : 멤버를 저장하기 위한 준비 과정
        UserEntity user = UserEntity.builder()
                .email("aaa@test.com")
                .password("1234")
                .name("max")
                .address("aaa-bbbb")
                .phone("010-1111-1111")
                .provider("Local")
                .role("ROLE_OWNER")
                .build();

        // when : 실제로 멤버를 저장
        userRepository.save(user);
        System.out.println("저장된 유저 이메일 : " + user.getEmail());

        // then : 멤버가 잘 추가되었는지 확인
        UserEntity ur = userRepository.findByEmail(user.getEmail());
        System.out.println("조회된 유저 이름 : " + ur.getName());
        assertThat(ur.getAddress()).isEqualTo(user.getAddress());
        assertThat(ur.getName()).isEqualTo(user.getName());
    }
}