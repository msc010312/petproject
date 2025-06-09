package com.example.demo.config.auth.loginHandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public class CustomLoginFailureHandler implements AuthenticationFailureHandler {

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
										AuthenticationException exception) throws IOException, ServletException {
		log.error("CustomLoginFailureHandler's on AuthenticationFailureHandler invoke");

		String errorMessage = "이메일 또는 비밀번호가 올바르지 않습니다.";

		if (exception.getMessage().contains("권한") || exception.getMessage().contains("ROLE")) {
			errorMessage = "권한을 다시 확인해주세요.";
		}

		request.getSession().setAttribute("loginErrorMessage", errorMessage);
		response.sendRedirect("/login");
	}

}
