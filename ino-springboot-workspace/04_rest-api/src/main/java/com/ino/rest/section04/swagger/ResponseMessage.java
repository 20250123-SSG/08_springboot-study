package com.ino.rest.section04.swagger;

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
