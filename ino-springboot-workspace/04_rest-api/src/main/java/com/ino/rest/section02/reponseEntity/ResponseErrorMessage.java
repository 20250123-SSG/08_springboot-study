package com.ino.rest.section02.reponseEntity;

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
