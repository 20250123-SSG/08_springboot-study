package com.ino.rest.section03;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class ResponseErrorMessage {
    private String code;
    private String message;
    private String describe;
}
