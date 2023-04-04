package com.bachtx.manga.repositories;

import com.bachtx.manga.models.Genre;
import com.bachtx.manga.models.Manga;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface MangaRepository extends JpaRepository<Manga, Long> {
    Page<Manga> findAllByActiveTrue(Pageable pageable);
    Page<Manga> findAllByGenres_GenreId(Long genreId, Pageable pageable);

}
