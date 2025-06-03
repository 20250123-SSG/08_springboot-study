package com.jjanggu.rest.section02.responseEntity;

import lombok.*;

import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ResponseMessage {
    private int status;     // 결과 코드
    private String message; // 결과 메세지
    private Map<String, Object> result; // 결과 데이터
}
