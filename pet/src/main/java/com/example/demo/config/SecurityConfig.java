package com.example.demo.config;

import com.example.demo.config.auth.loginHandler.CustomLoginFailureHandler;
import com.example.demo.config.auth.loginHandler.CustomLoginSuccessHandler;
import com.example.demo.config.auth.logoutHandler.CustomLogoutHandler;
import com.example.demo.config.auth.logoutHandler.CustomLogoutSuccessHandler;
import com.example.demo.config.auth.provider.CustomAuthenticationProvider;
import com.example.demo.service.CustomUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    private CustomLoginSuccessHandler customLoginSuccessHandler;

    @Autowired
    private CustomLoginFailureHandler customLoginFailureHandler;

    @Autowired
    private CustomLogoutHandler customLogoutHandler;

    @Autowired
    private CustomLogoutSuccessHandler customLogoutSuccessHandler;

    @Autowired
    private CustomUserDetailService customUserDetailService;

//    @Autowired
//    private PrincipalDetailsOAuth2Service principalDetailsOAuth2Service;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CustomAuthenticationProvider customAuthenticationProvider() {
        return new CustomAuthenticationProvider(customUserDetailService, passwordEncoder());
    }

    @Bean
    protected SecurityFilterChain configure(HttpSecurity http) throws Exception {
        // CSRF 비활성화
        http.csrf((config)->{config.disable();}); // csrf 토큰값확인x , get /logout 처리 가능

        // CSRF토큰을 쿠키형태로 전환
//		http.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());

        // 권한체크
        http.authorizeHttpRequests((auth)->{ auth
                .requestMatchers("/","/signup","/login","set-role","/reserve","/board/**").permitAll()
                .requestMatchers("/css/**","/js/**","/asset/**").permitAll()
//                .requestMatchers("/reserve").hasAnyRole("OWNER","SITTER","ADMIN")
                .requestMatchers("/board/add").hasAnyRole("OWNER","SITTER","ADMIN")
                .requestMatchers("/mypage/ownerpage").hasRole("OWNER")
                .requestMatchers("/mypage/sitterpage").hasRole("SITTER")
                .requestMatchers("/mypage/admin").hasRole("ADMIN")
                .anyRequest().authenticated();
        });

        // 로그인
        http.authenticationProvider(customAuthenticationProvider())
                .formLogin((login)->{ login
                .loginPage("/login")
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/main")
                .usernameParameter("email")
                .passwordParameter("password")
                .successHandler(customLoginSuccessHandler)
                .failureHandler(customLoginFailureHandler);
        });

        // 로그아웃
        http.logout((logout)->{ logout
                .logoutUrl("/logout")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .addLogoutHandler(customLogoutHandler)
                .logoutSuccessHandler(customLogoutSuccessHandler);
        });

        // 예외처리
        http.exceptionHandling((exception)->{
        });

        //OAUTH2-CLIENT
        http.oauth2Login((oauth2)->{ oauth2
                .successHandler(customLoginSuccessHandler)
                .loginPage("/login");
//                .userInfoEndpoint(userInfo -> userInfo
//                        .userService(principalDetailsOAuth2Service)
//                );
        });

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
