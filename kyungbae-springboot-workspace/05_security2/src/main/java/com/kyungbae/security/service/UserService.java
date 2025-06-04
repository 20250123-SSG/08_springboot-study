package com.kyungbae.security.service;

import com.kyungbae.security.dto.UserDto;
import com.kyungbae.security.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserMapper userMapper;
    private final BCryptPasswordEncoder passwordEncoder;

    public Map<String, String> registUser(UserDto user) {
        // 비밀번호 암호화
        user.setUserPwd(passwordEncoder.encode(user.getUserPwd()));

        String message = null;
        String path = null;
        // insert 쿼리
        try {
            userMapper.insertUser(user);
            message = "회원가입 완료";
            path= "/login";
        }catch (DuplicateKeyException e) { // id(key) 중복 검사
            e.printStackTrace();
            message="아이디 중복";
            path="/signup";
        }catch (Exception e) {
            e.printStackTrace();
            message="서버 오류";
            path="/signup";
        }

        return Map.of("message", message, "path", path);
    }

}
