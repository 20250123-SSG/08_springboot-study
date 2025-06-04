package com.podoseee.rest.section04.swagger;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Schema(description = "API 실패 응답 메세지")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ResponseErrorMessage {
    @Schema(description = "응답 에러 코드", allowableValues = {"00", "01", "02", "03"})
    private String code;
    @Schema(description = "응답 메세지")
    private String message;
    @Schema(description = "응답 상세 메세지")
    private String describe;
}