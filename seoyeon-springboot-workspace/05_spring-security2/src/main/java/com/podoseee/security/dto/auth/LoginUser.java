package com.podoseee.security.dto.auth;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

// 사용자 인증 정보를 담는 UserDetails 구현 클래스로 만들기
public class LoginUser extends User { /* implements UserDetails */

    private String displayName; // 회원명

    public LoginUser(String username, String password, Collection<? extends GrantedAuthority> authorities, String displayName) {
        super(username, password, authorities);
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
