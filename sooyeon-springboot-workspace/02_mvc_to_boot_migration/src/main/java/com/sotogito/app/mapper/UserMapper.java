package com.sotogito.app.mapper;

import com.sotogito.app.dto.UserDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

    int insertUser(UserDto user);
    int selectUserCountById(String checkId);
    UserDto selectUserById(String userId);
    int updateProfileImg(UserDto user);

}
