package com.ino.app.mapper;

import com.ino.app.dto.UserDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;


//@SpringBootTest
class UserMapperTest {

    //@Autowired
    private UserMapper userMapper;


    //@Test
    void insertUser() {
        UserDto user = UserDto.builder()
                .userId("junitTest")
                .userPwd("junitTest")
                .userName("junit")
                .email("junit@gmail.com")
                .gender("M")
                .address("seoul")
                .phone("010-1111-1111")
                .build();

        int result = userMapper.insertUser(user);
        org.assertj.core.api.Assertions.assertThat(result).isEqualTo(1);
    }

    //@Test
    void selectUserCountById() {
        String checkId = "admin01";

        int count = userMapper.selectUserCountById(checkId);

        Assertions.assertThat(count).isEqualTo(1);
    }

    //@Test
    void selectUserById() {

        String userId = "user01";

        UserDto user = userMapper.selectUserById(userId);

        Assertions.assertThat(user).isNotNull();
        Assertions.assertThat(user.getUserName()).isEqualTo("홍길동");
    }
}