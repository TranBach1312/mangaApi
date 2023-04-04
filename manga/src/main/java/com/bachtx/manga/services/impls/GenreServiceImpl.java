package com.bachtx.manga.services.impls;

import com.bachtx.manga.dto.request.GenreRequest;
import com.bachtx.manga.dto.response.GenreResponse;
import com.bachtx.manga.dto.response.MangaResponse;
import com.bachtx.manga.exceptions.AlreadyExistException;
import com.bachtx.manga.exceptions.NotFoundException;
import com.bachtx.manga.mapper.GenreMapper;
import com.bachtx.manga.mapper.MangaMapper;
import com.bachtx.manga.models.Genre;
import com.bachtx.manga.models.Manga;
import com.bachtx.manga.repositories.GenreRepository;
import com.bachtx.manga.repositories.MangaRepository;
import com.bachtx.manga.services.GenreService;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class GenreServiceImpl implements GenreService {
    private final GenreRepository genreRepository;
    private final MangaRepository mangaRepository;
    private GenreMapper genreMapper;
    private MangaMapper mangaMapper;
    @Value("${page.size}")
    private int pageSize;

    @Autowired
    public GenreServiceImpl(GenreRepository genreRepository, MangaRepository mangaRepository) {
        this.genreRepository = genreRepository;
        this.mangaRepository = mangaRepository;
    }

    @Override
    public List<GenreResponse> getAllGenres() {
        try {
            List<Genre> genres = genreRepository.findAll();
            if (genres.isEmpty()) {
                throw new NotFoundException("Genre not found");
            }
            List<GenreResponse> genreResponses = genreMapper.MAPPER.genreListToGenreResponseList(genres);
            return genreResponses;
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid input");
        }
    }

    @Override
    public GenreResponse getAllMangaByGenre(Long genreId, int page) {
        Sort sort = Sort.by("updatedAt").descending();
        Pageable pageable = PageRequest.of(page, pageSize, sort);
        try {
            List<Manga> mangas = mangaRepository.findAllByGenres_GenreId(genreId, pageable)
                    .stream().toList();
            List<MangaResponse> mangaResponses = mangaMapper.MAPPER.mangaListToMangaResponseList(mangas);
            Genre genre = genreRepository.findById(genreId)
                    .orElseThrow(() -> new NotFoundException("Genre not found"));
            if (mangas.isEmpty()) {
                throw new NotFoundException("This genre has 0 manga");
            }
            GenreResponse genreResponse = genreMapper.MAPPER.genreToGenreResponse(genre);
            genreResponse.setMangas(mangaResponses);
            return genreResponse;
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid input");
        }
    }

    @Override
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public GenreResponse addGenre(@Valid GenreRequest genreRequest) {
        try {
            boolean genreExists = genreRepository.existsByName(genreRequest.getName());
            if (genreExists) {
                throw new AlreadyExistException("Genre already exist");
            }
            Genre genre = genreMapper.MAPPER.genreRequestToGenre(genreRequest);
            Genre newGenre = genreRepository.save(genre);
            GenreResponse genreResponse = genreMapper.MAPPER.genreToGenreResponse(newGenre);
            return genreResponse;
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid input");
        }
    }

    @Override
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public GenreResponse updateGenre(Long genreId, GenreRequest genreRequest) {
        try {
            boolean genreExists = genreRepository.existsByNameAndGenreIdNot(genreRequest.getName(), genreId);
            if (genreExists) {
                throw new AlreadyExistException("Genre already exist");
            }
            Genre genre = genreMapper.MAPPER.genreRequestToGenre(genreRequest);
            genre.setGenreId(genreId);
            Genre updatedGenre = genreRepository.save(genre);

            GenreResponse genreResponse = genreMapper.MAPPER.genreToGenreResponse(updatedGenre);
            return genreResponse;
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid input");
        }
    }

    @Override
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public GenreResponse updateEnableStatusGenre(Long genreId, boolean isEnable) {
        try {
            Genre genre = genreRepository.findById(genreId)
                    .orElseThrow(() -> new NotFoundException("Genre not found"));
            genre.setEnable(isEnable);
            Genre updatedGenre = genreRepository.save(genre);
            GenreResponse genreResponse = genreMapper.MAPPER.genreToGenreResponse(updatedGenre);
            return genreResponse;
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid input");
        }
    }
}
