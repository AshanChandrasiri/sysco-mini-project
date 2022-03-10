package com.sysco.miniproject.data.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter

public class SignInReqDto {

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String password;

}
