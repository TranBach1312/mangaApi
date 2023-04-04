package com.bachtx.manga.dto.request;

import com.bachtx.manga.annotation.FieldMatch;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;

@Data
@Validated
@FieldMatch.List({
        @FieldMatch(first = "password", second = "rePassword", message = "The password fields must match")
}
)
public class RegisterRequest {
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
    @NotBlank
    @NotNull
    @NotEmpty
    @Length(min = 8)
    private String rePassword;
    @NotBlank
    @NotNull
    @NotEmpty
    @Email
    private String email;
}
