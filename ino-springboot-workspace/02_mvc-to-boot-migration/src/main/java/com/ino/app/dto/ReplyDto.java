package com.ino.app.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class ReplyDto {
    private int replyNo;
    private String replyContent;
    private String replyWriter;
    private int refBoardNo;
    private String registDate;
    private String status;
}
