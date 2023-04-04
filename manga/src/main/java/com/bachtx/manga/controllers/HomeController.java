package com.bachtx.manga.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/home")
public class HomeController {
    @GetMapping("")
    public ResponseEntity home(){
        return new ResponseEntity<>("abc", HttpStatus.OK);
    }
}
