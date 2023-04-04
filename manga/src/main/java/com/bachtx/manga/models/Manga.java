package com.bachtx.manga.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

import java.time.Instant;
import java.util.List;

@Data
@Entity
@Table(name = "mangas")
public class Manga {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long mangaId;
    private String title;
    private String description;
    private String author;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "publisher_id")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private User publisher;
    private String imageUrl;
    @Column(name = "is_active")
    private boolean active;
    @Column(name = "is_enable")
    private boolean enable;
    @Generated(GenerationTime.ALWAYS)
    private Instant createdAt;
    @Generated(GenerationTime.ALWAYS)
    private Instant updatedAt;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "manga", orphanRemoval = true, cascade = CascadeType.ALL)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<Chapter> chapters;
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "manga_genre",
            joinColumns = {@JoinColumn(name = "manga_id")},
            inverseJoinColumns = {@JoinColumn(name = "genre_id")}
    )
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<Genre> genres;
}
