package com.jjanggu.security.service;

import com.jjanggu.security.dto.UserDto;
import com.jjanggu.security.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

@RequiredArgsConstructor
@Service
public class UserServiceImpl {

    private final BCryptPasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    public Map<String, String> registUser(UserDto user){ // 아아디, 비번(평문), 이름, 권한

        user.setUserPwd(passwordEncoder.encode(user.getUserPwd())); // 아이디, 비번(암호문), 이름, 권한

        String message = null;
        String path = null;


        try {
            userMapper.insertUser(user);
            message = "회원 가입 성공";
            path = "/login";
        }catch (DuplicateKeyException e){
            e.printStackTrace();
            message = "중복된 아이디로 인한 회원 가입 실패";
            path = "/signup";
        }catch (Exception e){
            e.printStackTrace();
            message = "서버 내부 오류로 인한 회원 가입 실패";
            path = "/signup";
        }

        return Map.of("message", message, "path", path);
    }



}
