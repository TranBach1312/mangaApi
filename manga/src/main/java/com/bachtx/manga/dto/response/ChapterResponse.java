package com.bachtx.manga.dto.response;

import com.bachtx.manga.models.Manga;
import com.bachtx.manga.models.Page;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.Data;

import java.time.Instant;
import java.util.List;
@Data
public class ChapterResponse {
    private Long chapterId;
    private float chapterNumber;
    private String title;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private MangaResponse manga;
    private Instant createdAt;
    private Instant updatedAt;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<PageResponse> pages;
}
