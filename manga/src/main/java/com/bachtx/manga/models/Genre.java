package com.bachtx.manga.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "genres")
public class Genre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long genreId;
    private String name;
    private String description;
    private boolean isEnable;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @ManyToMany(mappedBy = "genres", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Manga> mangas;
}
