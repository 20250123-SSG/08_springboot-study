package com.ino.rest.section02.reponseEntity;

import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class UserDto {
    private int no;
    private String id;
    private String pwd;
    private String name;
    private LocalDateTime enrollDate;
}
