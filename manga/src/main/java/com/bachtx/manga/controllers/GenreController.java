package com.bachtx.manga.controllers;

import com.bachtx.manga.dto.response.GenreResponse;
import com.bachtx.manga.exceptions.NotFoundException;
import com.bachtx.manga.services.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/api/genre")
public class GenreController {
    private final GenreService genreService;
    @Autowired
    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    @GetMapping("/getAllGenres")
    public ResponseEntity getAllGenres(){
        try{
            List<GenreResponse> genreResponses = genreService.getAllGenres();
            return new ResponseEntity(genreResponses, HttpStatus.OK);
        } catch(IllegalArgumentException | NotFoundException e){
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/getMangaByGenre/{genreId}")
    public ResponseEntity getMangaByGenre( @PathVariable Long genreId, @RequestParam int page){
        try{
            GenreResponse genreResponse = genreService.getAllMangaByGenre(genreId, page);
            return new ResponseEntity(genreResponse, HttpStatus.OK);
        } catch(IllegalArgumentException | NotFoundException e){
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
