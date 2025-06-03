package com.jjanggu.app.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class BoardDto {
    private int boardNo;
    private String boardTitle;
    private String boardWriter; // 등록시:작성자회원번호, 조회시:작성자아이디
    private String boardContent;
    private int readCount;
    private String registDate; // 조회시:화면에 출력할 형식을 문자열로
    private String modifyDate; // 조회시:화면에 출력할 형식을 문자열로
    private String status;

    private int attachCount; // 첨부파일 개수
}
