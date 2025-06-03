package com.kyungbae.app.mapper;

import com.kyungbae.app.dto.UserDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

    int insertUser(UserDto user);
    int selectUserCountById(String checkID);
    UserDto selectUserById(String userId);
    int updateProfileImg(UserDto user);

}
