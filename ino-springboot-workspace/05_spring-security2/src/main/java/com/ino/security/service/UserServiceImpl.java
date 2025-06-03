package com.ino.security.service;

import com.ino.security.Mapper.UserMapper;
import com.ino.security.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public Map<String, String> registUser(UserDto user) {
        user.setUserPwd(passwordEncoder.encode(user.getUserPwd()));

        String msg = null;
        String path = null;
        try {
            userMapper.insertUser(user);
            msg = "regist user complete";
            path = "/login";
        } catch (DuplicateKeyException e) {
            e.printStackTrace();
            msg = "regist user failed : already exists";
            path = "/signup";
        } catch ( Exception e ) {
            e.printStackTrace();
            msg = "internal server error";
            path = "/signup";
        }

        return Map.of("message", msg, "path", path);
    }
}
