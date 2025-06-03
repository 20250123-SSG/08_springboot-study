package com.jjanggu.security.dto;

import lombok.*;

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
    private String userRole;

}
