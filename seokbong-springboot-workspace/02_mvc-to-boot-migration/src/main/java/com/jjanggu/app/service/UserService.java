package com.jjanggu.app.service;

import com.jjanggu.app.dto.UserDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface UserService {

    // 회원아이디수조회
    int getUserCount(String checkId);
    // 회원가입
    Map<String, Object> registUser(UserDto user);
    // 로그인
    Map<String, Object> loginUser(UserDto user);
    // 회원프로필수정
    Map<String, Object> modifyUserProfile(String UserId, MultipartFile file);
}
