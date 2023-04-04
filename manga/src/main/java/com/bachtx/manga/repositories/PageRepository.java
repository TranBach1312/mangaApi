package com.bachtx.manga.repositories;

import com.bachtx.manga.models.Page;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface PageRepository extends JpaRepository<Page, Long> {
}
