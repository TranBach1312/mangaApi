package com.bachtx.manga.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

import java.time.Instant;

@Data
@Entity
@Table(name = "pages")
public class Page {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pageId;
    private int pageNumber;
    private String imageUrl;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chapter_id")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Chapter chapter;
    @Generated(GenerationTime.ALWAYS)
    private Instant createdAt;
    @Generated(GenerationTime.ALWAYS)
    private Instant updatedAt;
}
