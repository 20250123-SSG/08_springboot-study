package com.younggalee.rest.section04.swagger;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Schema(description = "API 응답 실패 메시지")

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class ResponseErrorMessage {
    @Schema(description = "응답에러코드") // , allowableValues = {"00", "01"})
    private String code;
    @Schema(description = "응답에러메세지")
    private String message;
    @Schema(description = "응답상세메시지")
    private String describe;
}
