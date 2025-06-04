package com.ino.security.service;

import com.ino.security.dto.UserDto;

import java.util.Map;

public interface UserService {
    Map<String, String> registUser(UserDto user);
}
