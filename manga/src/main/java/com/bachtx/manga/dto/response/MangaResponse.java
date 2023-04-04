package com.bachtx.manga.dto.response;

import com.bachtx.manga.models.Chapter;
import com.bachtx.manga.models.Genre;
import com.bachtx.manga.models.User;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.Data;

import java.time.Instant;
import java.util.List;
@Data
public class MangaResponse {
    private Long mangaId;
    private String title;
    private String description;
    private String author;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private UserResponse publisher;
    private String imageUrl;
    private boolean isActive;
    private boolean isEnable;
    private Instant createdAt;
    private Instant updatedAt;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<ChapterResponse> chapters;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<GenreResponse> genres;
}
