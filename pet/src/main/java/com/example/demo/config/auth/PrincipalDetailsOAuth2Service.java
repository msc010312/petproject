package com.example.demo.config.auth;

import com.example.demo.config.auth.provider.GoogleUserInfo;
import com.example.demo.config.auth.provider.KakaoUserInfo;
import com.example.demo.config.auth.provider.NaverUserInfo;
import com.example.demo.config.auth.provider.OAuth2UserInfo;
import com.example.demo.domain.dto.UserDto;
import com.example.demo.domain.entity.OwnerEntity;
import com.example.demo.domain.entity.SitterEntity;
import com.example.demo.domain.entity.UserEntity;
import com.example.demo.domain.repository.OwnerRepository;
import com.example.demo.domain.repository.SitterRepository;
import com.example.demo.domain.repository.UserRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.Map;
import java.util.Optional;

@Service
public class PrincipalDetailsOAuth2Service extends DefaultOAuth2UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OwnerRepository ownerRepository;
    @Autowired
    private SitterRepository sitterRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private HttpSession session;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private HttpServletResponse response;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        //OAuth2UserInfo
        OAuth2User oAuth2User = super.loadUser(userRequest);
        System.out.println("oAuth2User : " + oAuth2User);
        System.out.println("getAttributes : " + oAuth2User.getAttributes());

        OAuth2UserInfo oAuth2UserInfo = null;
        //'kakao','naver','google','in-'
        String provider = userRequest.getClientRegistration().getRegistrationId();



        Map<String,Object> attributes = oAuth2User.getAttributes();
        if(provider.startsWith("kakao")) {
            //카카오 로그인시
            Long id = (Long) attributes.get("id");
            LocalDateTime connected_at = OffsetDateTime.parse(attributes.get("connected_at").toString()).toLocalDateTime();
            Map<String, Object> properties = (Map<String, Object>) attributes.get("properties");
            Map<String, Object> kakao_account = (Map<String, Object>) attributes.get("kakao_account");
            System.out.println("id :" + id);
            System.out.println("connected_at :" + connected_at);
            System.out.println("properties :" + properties);
            System.out.println("kakao_account :" + kakao_account);
            oAuth2UserInfo = new KakaoUserInfo(id, connected_at, properties, kakao_account);
        }
        else if(provider.startsWith("naver")) {
            //네이버 로그인시
            Map<String, Object> response = (Map<String, Object>) attributes.get("response");
            String id = (String) response.get("id");
            System.out.println("id : " + id);
            System.out.println("response : " + response);
            oAuth2UserInfo = new NaverUserInfo(id, response);

        }else if(provider.startsWith("google")){
            String id = (String)attributes.get("sub");
            System.out.println("google id : " + id);
            oAuth2UserInfo = new GoogleUserInfo(id,attributes);

        }

        //최초 로그인시 로컬계정 DB 저장 처리
        String username = oAuth2UserInfo.getName();
        String password = passwordEncoder.encode("1234");
        String email = oAuth2UserInfo.getEmail();
        String rawPhone = oAuth2UserInfo.getPhone();
        String prov = oAuth2UserInfo.getProvider();
        String phone = null;
        if (rawPhone != null) {
            phone = rawPhone.replace("+82 ", "0").replaceAll("-", "");
        }
        System.out.println(phone);
        String role = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("oauth2_role".equals(cookie.getName())) {
                    role = cookie.getValue();
                    cookie.setMaxAge(0);
                    cookie.setPath("/");
                    response.addCookie(cookie);
                }
            }
        }
        System.out.println("OAuth2 role from cookie: " + role);
        Optional<UserEntity> userOptional = Optional.ofNullable(userRepository.findByEmail(email));
        //UserDto 생성 (이유 : PrincipalDetails에 포함)
        //UserEntity 생성(이유 : 최초 로그인시 DB 저장용도)
        UserDto userDto = null;
        if(userOptional.isEmpty()){
            //최초 로그인(Dto , Entity)
            userDto = UserDto
                        .builder()
                        .email(email)
                        .password(password)
                        .phone(phone)
                        .role(role)
                        .name(username)
                        .createAt(LocalDateTime.now())
                        .provider(prov)
                        .build();
            UserEntity user = userDto.toEntity();
            userRepository.save(user);  //계정 등록

            // 역할별 엔티티 저장
            if ("ROLE_OWNER".equalsIgnoreCase(role)) {
                OwnerEntity owner = new OwnerEntity();
                owner.setUser(user);
                ownerRepository.save(owner);
            } else if ("ROLE_SITTER".equalsIgnoreCase(role)) {
                SitterEntity sitter = new SitterEntity();
                sitter.setUser(user);
                sitterRepository.save(sitter);
            }

        }else{
            //기존 유저 존재(Dto)
            UserEntity user = userOptional.get();
            userDto = UserDto.toDto(user);
        }


        // PrincipalDetails 전달
        PrincipalDetail principalDetail = new PrincipalDetail(userDto);
        userDto.setProvider(provider);
        userDto.setProviderId(oAuth2UserInfo.getProviderId());
        principalDetail.setUserDto(userDto);
        principalDetail.setAttributes(oAuth2User.getAttributes());
        principalDetail.setAccess_token(userRequest.getAccessToken().getTokenValue());
        return principalDetail;

    }
}
