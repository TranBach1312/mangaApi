package com.bachtx.manga.services;

import com.bachtx.manga.dto.request.MangaRequest;
import com.bachtx.manga.dto.response.MangaResponse;
import com.bachtx.manga.models.Manga;
import com.bachtx.manga.models.User;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

public interface MangaService {
    List<MangaResponse> getMangasInPage(int page);
    MangaResponse getMangaDetails(Long mangaId);
    MangaResponse addManga(MangaRequest mangaRequest, User user);
    MangaResponse updateInfoManga(Long mangaId, MangaRequest mangaRequest, User user);
    MangaResponse updateEnableStatus(Long mangaId, boolean isActive);
    MangaResponse updateActiveStatus(Long mangaId, boolean isActive, User user);
}
