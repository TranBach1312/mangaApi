package com.bachtx.manga.controllers;

import com.bachtx.manga.dto.response.MangaResponse;
import com.bachtx.manga.exceptions.NotFoundException;
import com.bachtx.manga.services.MangaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@Controller
@RequestMapping("/api/manga")
public class MangaController {
    private final MangaService mangaService;

    @Autowired
    public MangaController(MangaService mangaService) {
        this.mangaService = mangaService;
    }

    @GetMapping("/getAll")
    public ResponseEntity getAllManga(@RequestParam("page") int page) {
        try {
            List<MangaResponse> mangaResponses = mangaService.getMangasInPage(page);
            return new ResponseEntity(mangaResponses, HttpStatus.OK);
        } catch (IllegalArgumentException | NotFoundException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getMangaDetails")
    public ResponseEntity getDetailsManga(@RequestParam("mangaId") Long mangaId) {
        try {
            MangaResponse mangaResponse = mangaService.getMangaDetails(mangaId);
            return new ResponseEntity(mangaResponse, HttpStatus.OK);
        } catch (IllegalArgumentException | NotFoundException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
