package com.ino.rest.section03;

import lombok.*;

import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class ResponseMessage {
    private int status;
    private String message;
    private Map<String, Object> results;
}
