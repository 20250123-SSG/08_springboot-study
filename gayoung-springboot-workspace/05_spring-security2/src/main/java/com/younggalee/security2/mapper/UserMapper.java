package com.younggalee.security2.mapper;


import com.younggalee.security2.dto.UserDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    int insertUser(UserDto user); //처리된 행 수 리턴
    UserDto selectUserById(String userId);
}
