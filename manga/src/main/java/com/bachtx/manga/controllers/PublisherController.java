package com.bachtx.manga.controllers;

import com.bachtx.manga.dto.request.MangaRequest;
import com.bachtx.manga.dto.response.MangaResponse;
import com.bachtx.manga.exceptions.ForbiddenException;
import com.bachtx.manga.models.Manga;
import com.bachtx.manga.models.UserDetailsImpl;
import com.bachtx.manga.services.MangaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping("/api/publisher")
public class PublisherController {
    private final MangaService mangaService;

    @Autowired
    public PublisherController(MangaService mangaService) {
        this.mangaService = mangaService;
    }

    @PostMapping("/addManga")
    public ResponseEntity addManga(@Valid @ModelAttribute MangaRequest mangaRequest, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        try {
            MangaResponse mangaResponse = mangaService.addManga(mangaRequest, userDetails.getUser());
            return new ResponseEntity(mangaResponse, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @PutMapping("/updateInfoManga/{mangaId}")
    public ResponseEntity updateInfoManga(@PathVariable Long mangaId, @Valid @ModelAttribute MangaRequest mangaRequest, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        try {
            MangaResponse mangaResponse = mangaService.updateInfoManga(mangaId, mangaRequest, userDetails.getUser());
            return new ResponseEntity(mangaResponse, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (ForbiddenException e){
            return new ResponseEntity(e.getMessage(), HttpStatus. BAD_REQUEST);
        }
    }
    @PutMapping("/updateActiveStatusManga/{mangaId}")
    public ResponseEntity updateActiveStatus(@PathVariable Long mangaId, @RequestParam("isActive") boolean isActive, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        try {
            MangaResponse mangaResponse = mangaService.updateActiveStatus(mangaId, isActive, userDetails.getUser());
            return new ResponseEntity(mangaResponse, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (ForbiddenException e){
            return new ResponseEntity(e.getMessage(), HttpStatus. BAD_REQUEST);
        }
    }
}
