package org.example.controller;

import org.example.entity.dto.FilmDto;
import org.example.service.FilmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/films")
public class FilmsController {

    private final FilmService filmService;

    @Autowired
    public FilmsController(FilmService filmService) {
        this.filmService = filmService;
    }

    @GetMapping
    public ResponseEntity<List<FilmDto>> getAllFilms() {
        List<FilmDto> films = filmService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(films);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FilmDto> findFilmById(@PathVariable Long id) {
        FilmDto film = filmService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(film);
    }

    @PostMapping
    public ResponseEntity<FilmDto> createFilm(@RequestBody FilmDto filmDto) {
        FilmDto createdFilm = filmService.create(filmDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdFilm);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FilmDto> updateFilm(@PathVariable Long id, @RequestBody FilmDto filmDto) {
        FilmDto updatedFilm = filmService.update(filmDto, id);
        return ResponseEntity.status(HttpStatus.OK).body(updatedFilm);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteFilmById(@PathVariable Long id) {
        filmService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body("Film deleted");
    }
}