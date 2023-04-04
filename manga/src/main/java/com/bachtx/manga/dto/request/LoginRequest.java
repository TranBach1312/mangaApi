package com.bachtx.manga.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;

@Data
@Validated
public class  LoginRequest {
    @NotBlank
    @NotNull
    @NotEmpty
    @Length(min = 8)
    private String username;
    @NotBlank
    @NotNull
    @NotEmpty
    @Length(min = 8)
    private String password;
}
