package com.bachtx.manga.controllers;

import com.bachtx.manga.dto.request.GenreRequest;
import com.bachtx.manga.dto.response.GenreResponse;
import com.bachtx.manga.exceptions.AlreadyExistException;
import com.bachtx.manga.exceptions.NotFoundException;
import com.bachtx.manga.services.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/admin")
public class AdminController {
    private final GenreService genreService;

    @Autowired
    public AdminController(GenreService genreService) {
        this.genreService = genreService;
    }

    @PutMapping("/addGenre")
    public ResponseEntity addGenre(@RequestBody GenreRequest genreRequest) {
        try {
            GenreResponse genreResponse = genreService.addGenre(genreRequest);
            return new ResponseEntity(genreResponse, HttpStatus.CREATED);
        } catch (IllegalArgumentException | AlreadyExistException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/updateGenre/{genreId}")
    public ResponseEntity updateGenre(@PathVariable Long genreId, @RequestBody GenreRequest genreRequest) {
        try {
            GenreResponse genreResponse = genreService.updateGenre(genreId, genreRequest);
            return new ResponseEntity(genreResponse, HttpStatus.CREATED);
        } catch (IllegalArgumentException | AlreadyExistException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @PutMapping("/updateEnableStatus/{genreId}")
    public ResponseEntity updateEnableStatus( @PathVariable Long genreId, @RequestParam boolean isEnable){
        try{
            GenreResponse genreResponse = genreService.updateEnableStatusGenre(genreId, isEnable);
            return new ResponseEntity(genreResponse, HttpStatus.OK);
        } catch(IllegalArgumentException | NotFoundException e){
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
