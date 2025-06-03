package com.ino.app.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class AttachDto {
    private int fileNo;
    private String filePath;
    private String filesystemName;
    private String originalName;
    private String uploadDate;
    private int refBoardNo;
}
