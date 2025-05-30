package com.ino.rest.section04.swagger;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Schema(description = "API Response Success Message")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class ResponseErrorMessage {

    @Schema(description = "응답 에러 코드", allowableValues = {"00", "01", "02", "03"})
    private String code;

    @Schema(description = "응답 에러 메세지")
    private String message;

    @Schema(description = "응답 상세 메세지")
    private String describe;
}
