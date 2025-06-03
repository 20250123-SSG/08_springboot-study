package com.younggalee.security2.dto.auth;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;

// 사용자의 인증정보를 담는 UserDetails 구현 클래스로 만들기
// DB 전송용은 아님 (DTO형태이긴하나 Dto역할은 아님)
public class LoginUser extends User {
    public String getDisplayName() {
        return displayName;
    }

    // UserDetails는 User를 상속받는 인터페이스. 따라서 UserDetails로 구현하려면 오버라이딩해야할게 많음
    //그래서 User클래스 상속받겠음
    private String displayName; // 회원명

    //userRole를 collection형태의 권한목록으로 만들어서 넘겨주어야함.
    public LoginUser(String username, String password, Collection<? extends GrantedAuthority> authorities, String displayName) {
        super(username, password, authorities);
        this.displayName = displayName;
    }


}
