package com.bachtx.manga.mapper;


import com.bachtx.manga.dto.request.MangaRequest;
import com.bachtx.manga.dto.response.MangaResponse;
import com.bachtx.manga.models.Manga;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;


@Mapper
public interface MangaMapper {
    MangaMapper MAPPER = Mappers.getMapper(MangaMapper.class);

    @Mappings({
            @Mapping(target = "chapters", ignore = true),
            @Mapping(target = "publisher", ignore = true),
            @Mapping(target = "genres", ignore = true)
    })
    MangaResponse mangaToMangaResponse(Manga manga);

    @Mappings({
            @Mapping(target = "chapters", ignore = true),
            @Mapping(target = "publisher", ignore = true),
            @Mapping(target = "genres", ignore = true)
    })
    List<MangaResponse> mangaListToMangaResponseList(List<Manga> mangas);

    @Mappings({
            @Mapping(target = "publisher", ignore = true),
            @Mapping(target = "mangaId", ignore = true)
    })
    Manga mangaRequestToManga(MangaRequest mangaRequest);
}
