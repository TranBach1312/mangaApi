package com.bachtx.manga.services.impls;

import com.bachtx.manga.dto.request.MangaRequest;
import com.bachtx.manga.dto.response.ChapterResponse;
import com.bachtx.manga.dto.response.GenreResponse;
import com.bachtx.manga.dto.response.MangaResponse;
import com.bachtx.manga.dto.response.UserResponse;
import com.bachtx.manga.exceptions.ForbiddenException;
import com.bachtx.manga.exceptions.NotFoundException;
import com.bachtx.manga.mapper.ChapterMapper;
import com.bachtx.manga.mapper.GenreMapper;
import com.bachtx.manga.mapper.MangaMapper;
import com.bachtx.manga.mapper.UserMapper;
import com.bachtx.manga.models.Genre;
import com.bachtx.manga.models.Manga;
import com.bachtx.manga.models.User;
import com.bachtx.manga.repositories.GenreRepository;
import com.bachtx.manga.repositories.MangaRepository;
import com.bachtx.manga.repositories.UserRepository;
import com.bachtx.manga.services.MangaService;
import com.bachtx.manga.utils.MediaUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@Transactional
public class MangaServiceImpl implements MangaService {
    private final MangaRepository mangaRepository;
    private final GenreRepository genreRepository;
    private final UserRepository userRepository;
    @PersistenceContext
    private EntityManager entityManager;
    private MangaMapper mangaMapper;
    private ChapterMapper chapterMapper;
    private GenreMapper genreMapper;
    private UserMapper userMapper;
    private final MediaUtil mediaUtil;
    @Value("${page.size}")
    private int pageSize;

    @Autowired
    public MangaServiceImpl(MangaRepository mangaRepository, GenreRepository genreRepository, UserRepository userRepository, MediaUtil mediaUtil) {
        this.mangaRepository = mangaRepository;
        this.genreRepository = genreRepository;
        this.userRepository = userRepository;
        this.mediaUtil = mediaUtil;
    }

    @Override
    public List<MangaResponse> getMangasInPage(int page) {
        try {
            Sort sort = Sort.by("updatedAt").descending();
            Pageable pageable = PageRequest.of(page, pageSize, sort);
            List<Manga> mangas = mangaRepository.findAllByActiveTrue(pageable).stream().toList();
            List<MangaResponse> mangaResponses = mangaMapper.MAPPER.mangaListToMangaResponseList(mangas);
            if (mangaResponses.isEmpty()) {
                throw new NotFoundException("No manga found");
            }
            return mangaResponses;
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException("Invalid input");
        }
    }

    @Override
    public MangaResponse getMangaDetails(Long mangaId) {
        try {
            Manga manga = mangaRepository.findById(mangaId)
                    .orElseThrow(() -> new NotFoundException("Manga not found"));
            MangaResponse mangaResponse = mangaMapper.MAPPER.mangaToMangaResponse(manga);
            List<ChapterResponse> chapterResponses = chapterMapper.MAPPER.chapterListToChapterResponseList(manga.getChapters());
            List<GenreResponse> genreResponses = genreMapper.MAPPER.genreListToGenreResponseList(manga.getGenres());
            UserResponse userResponse = userMapper.MAPPER.userToUserResponse(manga.getPublisher());
            mangaResponse.setChapters(chapterResponses);
            mangaResponse.setGenres(genreResponses);
            mangaResponse.setPublisher(userResponse);
            return mangaResponse;
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException("Invalid input");
        }
    }

    @Override
    @PreAuthorize("hasAnyRole('ROLE_PUBLISHER', 'ROLE_ADMIN')")
    public MangaResponse addManga(@Valid MangaRequest mangaRequest, User user) {
        try {
            Manga manga = mangaMapper.MAPPER.mangaRequestToManga(mangaRequest);
            User requestUser = userRepository.findById(user.getUserId())
                    .orElseThrow(() -> new NotFoundException("User not found"));
            List<Genre> genres = genreRepository.findAllById(mangaRequest.getGenresId());
            manga.setGenres(genres);
            String imageUrl = mediaUtil.uploadImage(mangaRequest.getImage(), "image");
            manga.setPublisher(requestUser);
            manga.setImageUrl(imageUrl);
            manga.setEnable(true);
            manga.setActive(true);
            Manga newManga = mangaRepository.save(manga);
            MangaResponse mangaResponse = mangaMapper.MAPPER.mangaToMangaResponse(newManga);
            mangaResponse.setGenres(genreMapper.MAPPER.genreListToGenreResponseList(manga.getGenres()));
            return mangaResponse;
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid input");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @PreAuthorize("hasAnyRole('ROLE_PUBLISHER', 'ROLE_ADMIN')")
    public MangaResponse updateInfoManga(Long mangaId, MangaRequest mangaRequest, User user) {
        try {
            Manga manga = mangaRepository.findById(mangaId)
                    .orElseThrow(() -> new NotFoundException("Manga not found"));
            User requestUser = userRepository.findById(user.getUserId())
                    .orElseThrow(() -> new NotFoundException("User not found"));
            if (requestUser != manga.getPublisher()) {
                throw new ForbiddenException("You do not have permission to update");
            }
            manga = mangaMapper.MAPPER.mangaRequestToManga(mangaRequest);
            manga.setPublisher(requestUser);
            List<Genre> genres = genreRepository.findAllById(mangaRequest.getGenresId());
            manga.setGenres(genres);
            String imageUrl = mediaUtil.uploadImage(mangaRequest.getImage(), "image");
            manga.setImageUrl(imageUrl);
            manga.setMangaId(mangaId);
            Manga updatedManga = mangaRepository.save(manga);
            entityManager.refresh(updatedManga);
            MangaResponse mangaResponse = mangaMapper.MAPPER.mangaToMangaResponse(updatedManga);
            return mangaResponse;
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid input");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public MangaResponse updateEnableStatus(Long mangaId, boolean isEnable) {
        Manga manga = mangaRepository.findById(mangaId)
                .orElseThrow(() -> new NotFoundException("Manga not found"));
        manga.setEnable(isEnable);
        Manga updatedManga = mangaRepository.save(manga);
        entityManager.flush();
        entityManager.refresh(updatedManga);
        MangaResponse mangaResponse = mangaMapper.MAPPER.mangaToMangaResponse(updatedManga);
        return mangaResponse;
    }

    @Override
    @PreAuthorize("hasAnyRole('ROLE_PUBLISHER')")
    public MangaResponse updateActiveStatus(Long mangaId, boolean isActive, User user) {
        Manga manga = mangaRepository.findById(mangaId)
                .orElseThrow(() -> new NotFoundException("Manga not found"));
        User requestUser = userRepository.findById(user.getUserId())
                .orElseThrow(() -> new NotFoundException("User not found"));
        if (requestUser != manga.getPublisher()) {
            throw new ForbiddenException("You do not have permission to update");
        }
        manga.setActive(isActive);
        Manga updatedManga = mangaRepository.save(manga);
        entityManager.flush();
        entityManager.refresh(updatedManga);
        MangaResponse mangaResponse = mangaMapper.MAPPER.mangaToMangaResponse(updatedManga);
        return mangaResponse;
    }
}