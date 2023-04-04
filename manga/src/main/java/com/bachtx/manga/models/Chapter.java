package com.bachtx.manga.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

import java.time.Instant;
import java.util.List;

@Data
@Entity
@Table(name = "chapters")
public class Chapter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long chapterId;
    private float chapterNumber;
    private String title;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "manga_id")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Manga manga;
    @Generated(GenerationTime.ALWAYS)
    private Instant createdAt;
    @Generated(GenerationTime.ALWAYS)
    private Instant updatedAt;
    @OneToMany(mappedBy = "chapter", fetch = FetchType.LAZY)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<Page> pages;
}
