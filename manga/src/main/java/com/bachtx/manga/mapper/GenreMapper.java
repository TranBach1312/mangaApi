package com.bachtx.manga.mapper;

import com.bachtx.manga.dto.request.GenreRequest;
import com.bachtx.manga.dto.response.GenreResponse;
import com.bachtx.manga.models.Genre;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface GenreMapper {
    GenreMapper MAPPER = Mappers.getMapper(GenreMapper.class);

    @Mappings({
            @Mapping(target = "mangas", ignore = true)
    })
    GenreResponse genreToGenreResponse(Genre genre);

    @Mappings({
            @Mapping(target = "mangas", ignore = true)
    })
    List<GenreResponse> genreListToGenreResponseList(List<Genre> genres);
    Genre genreRequestToGenre(GenreRequest genreRequest);
}
