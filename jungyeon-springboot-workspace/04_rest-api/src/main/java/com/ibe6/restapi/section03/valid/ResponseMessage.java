package com.ibe6.restapi.section03.valid;

import lombok.*;

import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder

public class ResponseMessage {
    private int status;         // 결과코드
    private String message;     // 결과메세지
    private Map<String, Object> results;     // 결과 데이터
}
