package com.podoseee.rest.section03.valid;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ResponseErrorMessage {
    private String code;
    private String message;
    private String describe;
}
