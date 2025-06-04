package com.younggalee.rest.section03.Valid;

import lombok.*;

import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class ResponseMessage {
    private int status;        // 결과 코드
    private String message;    // 결과 메세지
    private Map<String, Object> results; // 결과 데이터
}
