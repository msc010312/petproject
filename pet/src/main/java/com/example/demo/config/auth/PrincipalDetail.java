package com.example.demo.config.auth;

import com.example.demo.domain.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "userDto")
public class PrincipalDetail implements UserDetails,OAuth2User {
    private UserDto userDto;
    public PrincipalDetail(UserDto userDto){
        this.userDto = userDto;
    }
    //----------------------------
    // OAuth2User
    //----------------------------
    Map<String, Object> attributes;
    String access_token;
    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }
    @Override
    public String getName() {
        return userDto.getName();
    }
    //----------------------------


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection <GrantedAuthority> authorities = new ArrayList();
        authorities.add(new SimpleGrantedAuthority(userDto.getRole()));
        return authorities;
    }

    @Override
    public String getPassword() {
        // TODO Auto-generated method stub
        return userDto.getPassword();
    }

    @Override
    public String getUsername() {
        // TODO Auto-generated method stub
        return userDto.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean isEnabled() {
        // TODO Auto-generated method stub
        return true;
    }

}
