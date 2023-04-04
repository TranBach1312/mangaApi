package com.bachtx.manga.services;

import com.bachtx.manga.dto.request.GenreRequest;
import com.bachtx.manga.dto.response.GenreResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

public interface GenreService {
    List<GenreResponse> getAllGenres();
    GenreResponse getAllMangaByGenre(Long genreId, int page);
    GenreResponse addGenre(GenreRequest genreRequest);
    GenreResponse updateGenre(Long genreId, GenreRequest genreRequest);
    GenreResponse updateEnableStatusGenre(Long genreId, boolean isEnable);
}
