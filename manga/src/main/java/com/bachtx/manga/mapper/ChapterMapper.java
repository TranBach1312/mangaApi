package com.bachtx.manga.mapper;

import com.bachtx.manga.dto.response.ChapterResponse;
import com.bachtx.manga.models.Chapter;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ChapterMapper {
    ChapterMapper MAPPER = Mappers.getMapper(ChapterMapper.class);

    @Mappings({
            @Mapping(target = "pages", ignore = true),
            @Mapping(target = "manga", ignore = true)
    })
    ChapterResponse chapterToChapterResponse(Chapter chapter);

    @Mappings({
            @Mapping(target = "pages", ignore = true),
            @Mapping(target = "manga", ignore = true)
    })
    List<ChapterResponse> chapterListToChapterResponseList(List<Chapter> chapters);
}
