package com.bachtx.manga.dto.request;

import com.bachtx.manga.models.Manga;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.ManyToMany;
import lombok.Data;

import java.util.List;
@Data
public class GenreRequest {
    private String name;
    private String description;
}
