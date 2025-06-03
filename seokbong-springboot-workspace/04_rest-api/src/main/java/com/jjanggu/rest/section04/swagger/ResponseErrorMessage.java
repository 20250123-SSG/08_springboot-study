package com.jjanggu.rest.section04.swagger;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Schema(description = "API 응답 실패 메세지")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ResponseErrorMessage {
    @Schema(description = "응답 에러 코드", allowableValues = {"00", "01","02", "03"})
    private String code;    // 응답 에러 코드
    @Schema(description = "응답 메세지")
    private String message; // 응답 에러 메세지
    @Schema(description = "응답 상세 메세지")
    private String describe; // 응답 에러 상세 메세지
}
