package com.bachtx.manga.repositories;

import com.bachtx.manga.models.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Long> {
    boolean existsByName(String name);
    boolean existsByNameAndGenreIdNot(String name, Long genreId);
}
