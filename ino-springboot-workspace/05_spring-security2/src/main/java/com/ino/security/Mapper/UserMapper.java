package com.ino.security.Mapper;

import com.ino.security.dto.UserDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    int insertUser(UserDto user);
    UserDto selectUserById(String userId);
}
