package com.younggalee.thymeleaf.dto;

import lombok.*;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder // 이렇게 붙이면 build의 UserDto들어가보면 어떻게 생성됐는지 확인해볼 수 있음.
public class UserDto {

    private int no;
    private String name;
    private int age;
    private LocalDate registDate;

}
