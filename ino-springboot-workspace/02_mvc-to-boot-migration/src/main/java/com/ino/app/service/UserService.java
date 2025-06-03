package com.ino.app.service;

import com.ino.app.dto.UserDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface UserService {

    //회원 아이디 수 조회
    int getUserCount(String checkId);
    // 회원 가입
    Map<String, Object> registUser(UserDto user);
    //login
    Map<String, Object> loginUser(UserDto user);
    //mod profile
    Map<String, Object> modifyUserProfile(String UserId, MultipartFile file);
}
