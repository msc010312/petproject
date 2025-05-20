package com.example.demo.config.auth.loginHandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import java.io.IOException;

@Slf4j
public class CustomLoginFailureHandler implements AuthenticationFailureHandler {

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {
		log.error("CustomLoginFailureHandler's on AuthenticationFailureHandler invoke");
		request.getSession().setAttribute("loginErrorMessage", "이메일 또는 비밀번호가 올바르지 않습니다.");
		response.sendRedirect("/login"); // 에러 쿼리 안 씀 (한글 깨짐 방지)
	}

}
