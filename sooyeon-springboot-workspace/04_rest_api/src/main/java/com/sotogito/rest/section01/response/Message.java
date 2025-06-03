package com.sotogito.rest.section01.response;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Message {

    private int status;
    private String message;
}
