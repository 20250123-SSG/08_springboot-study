package com.johnth.rest.section03.valid;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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

    @NotNull(message="아이디는 반드시 입력되어야 합니다.", groups=RegistGroup.class)
    @NotBlank(message="아이디는 공백일 수 없습니다.", groups=RegistGroup.class)
    private String id;

    @NotNull(message="비밀번호는 반드시 입력되어야 합니다.", groups={RegistGroup.class, ModifyGroup.class})
    @Size(min=4, max=20, message="비밀번호는 4~20자 이여야 됩니다.", groups={RegistGroup.class, ModifyGroup.class})
    private String pwd;

    @NotBlank(message="이름은 반드시 입력되어야 합니다.", groups={RegistGroup.class, ModifyGroup.class})
    @Size(min=2, message="이름은 2글자 이상 이여야 됩니다.", groups={RegistGroup.class, ModifyGroup.class})
    private String name;

    private LocalDateTime enrollDate;
}
