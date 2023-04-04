package com.bachtx.manga.dto.response;

import com.bachtx.manga.models.Role;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.Instant;
import java.util.List;

@Data
public class UserResponse {
    private Long userId;
    private String username;
    private String email;
    private Role role;
    private Instant createdAt;
    private Instant updatedAt;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<MangaResponse> mangas;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String token;
}