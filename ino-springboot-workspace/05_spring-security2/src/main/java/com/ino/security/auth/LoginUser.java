package com.ino.security.auth;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class LoginUser extends User {

    private String displayName;

    public LoginUser(String username, String password, Collection<? extends GrantedAuthority> authorities, String displayName) {
        super(username, password, authorities);
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
