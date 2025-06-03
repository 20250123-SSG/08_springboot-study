package com.sotogito.rest.section02.responseEntity;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ResponseErrorMessage {

    private String code; //에러코드
    private String message; //에러메시지
    private String description; //에러상세메시지

}
