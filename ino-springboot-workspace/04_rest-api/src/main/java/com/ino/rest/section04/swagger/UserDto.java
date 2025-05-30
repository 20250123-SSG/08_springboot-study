package com.ino.rest.section04.swagger;

import com.ino.rest.section03.ModifyGroup;
import com.ino.rest.section03.RegistGroup;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class UserDto {

    @Schema(description = "회원번호", defaultValue = "auto_increment", example = "1")
    private int no;


    @NotNull(message = "id must be input")
    @NotBlank(message = "id cannot be blank")
    private String id;

    @NotNull(message = "pwd must be input")
    @Size(min = 4, max = 20, message = "pwd 4~20")
    private String pwd;

    @NotBlank(message = "name must be input")
    @Size(min = 2, message = "name size gt 2")
    private String name;

    @Null
    private LocalDateTime enrollDate;
}
