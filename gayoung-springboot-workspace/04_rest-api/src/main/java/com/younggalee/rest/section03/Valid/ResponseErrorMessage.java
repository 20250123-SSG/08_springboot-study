package com.younggalee.rest.section03.Valid;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class ResponseErrorMessage {
    private String code; // 응답에러코드
    private String message; // 응답에러메세지
    private String describe; // 응답상세메시지
}
