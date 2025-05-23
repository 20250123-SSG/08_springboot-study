package com.jjanggu.thymeleaf.dto;

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
    private String name;
    private int age;
    private LocalDateTime registDate;

}
