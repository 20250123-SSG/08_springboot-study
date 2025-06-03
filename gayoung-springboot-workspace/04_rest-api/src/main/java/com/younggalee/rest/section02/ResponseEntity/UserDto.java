package com.younggalee.rest.section02.ResponseEntity;

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
    private String password;
    private String name;
    private LocalDateTime createdDt;
}
