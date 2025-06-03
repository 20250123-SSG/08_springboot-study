package com.ino.rest.section03;

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
    private int no;

    @NotNull(message = "id must be input", groups = RegistGroup.class)
    @NotBlank(message = "id cannot be blank", groups = RegistGroup.class)
    private String id;

    @NotNull(message = "pwd must be input", groups = {RegistGroup.class, ModifyGroup.class})
    @Size(min = 4, max = 20, message = "pwd 4~20",groups = {RegistGroup.class, ModifyGroup.class})
    private String pwd;

    @NotBlank(message = "name must be input", groups = {RegistGroup.class, ModifyGroup.class})
    @Size(min = 2, message = "name size gt 2", groups = {RegistGroup.class, ModifyGroup.class})
    private String name;

    @Null
    private LocalDateTime enrollDate;
}
