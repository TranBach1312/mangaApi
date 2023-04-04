package com.bachtx.manga.dto.response;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;
@Data
public class GenreResponse {
    private Long genreId;
    private String name;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<MangaResponse> mangas;
}
