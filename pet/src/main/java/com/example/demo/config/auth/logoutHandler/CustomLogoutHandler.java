package com.example.demo.config.auth.logoutHandler;


import com.example.demo.config.auth.PrincipalDetail;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CustomLogoutHandler implements LogoutHandler {


	@Override
	public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {

		log.info("CustomLogoutHandler's logout invoke");

		HttpSession session =  request.getSession();
		if(session!=null)
			session.invalidate();

		if (authentication != null) {
			String username = authentication.getName();
			System.out.println("로그아웃: " + username);
		}

		PrincipalDetail principalDetails = (PrincipalDetail)authentication.getPrincipal();
		String provider = principalDetails.getUserDto().getProvider();
		if(provider!=null && provider.startsWith("kakao")){

		}else if(provider!=null && provider.startsWith("naver")){

		}else if(provider!=null && provider.startsWith("google")){

		}

	}

}
