package com.younggalee.async.dto;

import lombok.*;

import java.time.LocalDateTime;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class BoardDto {
    private int boardId;
    private String title;
    private String content;
    private LocalDateTime createDt;
}
