package com.sotogito.app.dto;

import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class UserDto {
    private int userNo;
    private String userId;
    private String userPwd;
    private String userName;
    private String email;
    private String gender;
    private String phone;
    private String address;
    private String profileURL;
    private LocalDateTime signupDate;
    private LocalDateTime modifyDate;
    private String role;
}
