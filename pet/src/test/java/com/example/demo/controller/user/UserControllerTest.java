package com.example.demo.controller.user;

import com.example.demo.domain.entity.UserEntity;
import com.example.demo.domain.repository.UserRepository;
import com.example.demo.service.UserServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private UserRepository userRepository;

    @Mock
    private UserServiceImpl userService;

    @BeforeEach // 테스트 실행 전 실행하는 메서드
    public void mockMvcSetup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @AfterEach // 테스트 실행 후 실행하는 메서드
    public void cleanUp() {
        userRepository.deleteAll();
    }

//    @Test
//    @DisplayName("get /signup test")
//    void signup() throws Exception {
//        mockMvc.perform(get("/signup"))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(view().name("sign/signup"));
//    }

    @Test
    @DisplayName("POST /signup - 회원가입 성공 시 로그인 페이지로 이동")
    void registerUser_success() throws Exception {
        // Given
        String email = "test@example.com";
        String password = "Test@1234";
        String phone = "01012345678";
        String userPwRe = password;
        String name = "tester";
        String address = "abc-def";
        String role = "ROLE_OWNER";
        String provider = "Local";
        LocalDateTime createAt = LocalDateTime.now();

        // When
        ResultActions result = mockMvc.perform(post("/signup")
                .param("email", email)
                .param("password", password)
                .param("phone", phone)
                .param("userPwRe", userPwRe)
                .param("name", name)
                .param("role", role)
                .param("address", address)
                .param("provider", provider)
                .param("createAt", String.valueOf(createAt))
        );

        // Then
        result.andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("sign/login"));

        // userService.registerUser() 호출 여부 검증
//        verify(userService, times(1)).registerUser(any(UserEntity.class));
    }

    //    @Test
//    void login() {
//    }
//
//    @Test
//    void logout() {
//    }
//
//    @Test
//    void registerUser() {
//    }
//

@WithMockUser(username = "test1234@test.com", roles = {"OWNER"})
@Test
@DisplayName("POST /delete - 회원 탈퇴")
void deleteUser() throws Exception {
    // given
    String testEmail = "test1234@test.com";

    UserEntity user = UserEntity.builder()
            .email(testEmail)
            .password("Test@1234")
            .name("tester")
            .phone("01011112222")
            .address("seoul")
            .role("ROLE_OWNER")
            .provider("Local")
            .build();

    userRepository.save(user);
    userRepository.flush(); // 추가

    // when
    ResultActions result = mockMvc.perform(post("/delete")).andDo(print());

    // then
    result.andExpect(status().isOk())
            .andExpect(view().name("sign/delete-success"))
            .andExpect(model().attribute("message", "회원 탈퇴가 완료되었습니다."));
}
//
//    @Test
//    void setRole() {
//    }
//
//    @Test
//    void getUserService() {
//    }
//
//    @Test
//    void getUserRepository() {
//    }
//
//    @Test
//    void setUserService() {
//    }
//
//    @Test
//    void setUserRepository() {
//    }
}