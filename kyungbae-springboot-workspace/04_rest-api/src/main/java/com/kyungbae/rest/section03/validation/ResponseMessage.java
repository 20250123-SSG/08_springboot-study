package com.kyungbae.rest.section03.validation;

import lombok.*;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class ResponseMessage {
    private int status;     // 결과 코드
    private String message; // 결과 메세지
    private Map<String, Object> results; // 결과 데이터
}
