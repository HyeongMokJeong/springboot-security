package com.cos.security1.config.auth;

// 시큐리티가 /login 주소 요청을 낚아채서 로그인 진행시킴
// 로그인 진행 완료되면 session 안의 시큐리티 공간을 만들어줌(Security ContextHolder)
// 시큐리티 공간에 들어갈 수 있는 오브젝트 -> Authentication 객체
// Authentication 안에는 User 정보
// User 오브젝트 타입 -> UserDetails 타입 객체

// Security Session => Security ContextHolder : Authentication => UserDetails(PrincipalDetails)

import com.cos.security1.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

public class PrincipalDetails implements UserDetails {

    private User user;

    public PrincipalDetails(User user) {
        this.user = user;
    }

    // 해당 User의 권한을 return
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collect = new ArrayList<>();
        collect.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return user.getRole();
            }
        });
        return collect;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    // 계정 만료?
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // 계정 잠금?
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // 계정 비밀번호 변경주기?
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // 계정 활성화?
    @Override
    public boolean isEnabled() {
        return true;
    }
}
