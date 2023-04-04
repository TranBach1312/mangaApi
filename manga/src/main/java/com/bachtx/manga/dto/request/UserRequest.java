package com.bachtx.manga.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

@Data
@Validated
public class UserRequest {
    private Long userId;
    private String username;
    @Email
    private String email;
}
