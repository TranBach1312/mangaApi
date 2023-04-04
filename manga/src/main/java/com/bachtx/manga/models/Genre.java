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
    @ManyToMany(mappedBy = "genres")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<Manga> mangas;
}
