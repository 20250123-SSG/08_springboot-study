package com.younggalee.security2.service;

import com.younggalee.security2.dto.UserDto;
import com.younggalee.security2.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@RequiredArgsConstructor
@Service
public class UserServiceImpl {

    private final BCryptPasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    public Map<String, String> registUser(UserDto user) {
        // 아이디, 비번(평문), 이름, 권한
        // 암호화한 후, set으로 담기

        user.setUserPwd(passwordEncoder.encode(user.getUserPwd()));

        //메세지, redirect 경로를 map으로 담아 전달

        String message = null;
        String path = "/login";

        try {
            userMapper.insertUser(user);
            message = "회원가입 성공";
        } catch (DuplicateKeyException e) {
            e.printStackTrace(); // 키 중복에러
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Map.of("message", message, "path", path);
    }
}

