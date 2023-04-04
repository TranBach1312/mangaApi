package com.bachtx.manga.dto.response;

import com.bachtx.manga.models.Chapter;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.time.Instant;
@Data
public class PageResponse {
    private Long pageId;
    private int pageNumber;
    private String imageUrl;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private ChapterResponse chapter;
    private Instant createdAt;
    private Instant updatedAt;
}
