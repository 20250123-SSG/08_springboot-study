package com.ino.rest.section02.reponseEntity;

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
