//package com.example.demo.config.auth;
//
//import com.example.demo.domain.dto.UserDto;
//import com.example.demo.domain.entity.UserEntity;
//import com.example.demo.domain.repository.UserRepository;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//@Service
//public class PrincipalDetailsService implements UserDetailsService {
//
//    private final UserRepository userRepository;
//
//    public PrincipalDetailsService(UserRepository userRepository) {
//        this.userRepository = userRepository;
//    }
//
//    @Override
//    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//        UserEntity userEntity = userRepository.findByEmail(email);
//        if (userEntity == null) {
//            throw new UsernameNotFoundException("사용자를 찾을 수 없습니다: " + email);
//        }
//
//        UserDto userDto = UserDto.toDto(userEntity);
//        return new PrincipalDetail(userDto);
//    }
//}
