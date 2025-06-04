package com.younggalee.rest.section04.swagger;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.Map;

@Schema(description = "API 성공 응답 메시지")

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class ResponseMessage {
    @Schema(description = "결과 코드", allowableValues = {"200"})
    private int status;
    @Schema(description = "결과 메세지")
    private String message;
    @Schema(description = "결과 데이터")
    private Map<String, Object> results;
}
