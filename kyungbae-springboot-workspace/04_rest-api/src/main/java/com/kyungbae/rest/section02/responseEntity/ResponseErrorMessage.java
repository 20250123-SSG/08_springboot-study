package com.kyungbae.rest.section02.responseEntity;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class ResponseErrorMessage {
    private String code; // 응답 에러 코드
    private String message; // 응답 에러 메세지
    private String describe; // 응답 에러 상세 메세지
}
