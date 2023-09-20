package org.example.service;

import org.example.entity.Film;
import org.example.entity.dto.FilmDto;
import org.example.mapper.FilmRowMapper;
import org.example.repository.FilmRepository;
import org.example.service.impl.FilmServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityNotFoundException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FilmServiceImplTest {

    @Mock
    private FilmRepository filmRepository;

    @Mock
    private FilmRowMapper filmRowMapper;

    private FilmService filmService;

    @BeforeEach
    void setUp() {
        filmService = new FilmServiceImpl(filmRepository, filmRowMapper);
    }

    @Test
    void testFindById() {
        Long filmId = 1L;
        Film film = new Film();
        film.setId(filmId);
        film.setTitle("Test Film");

        when(filmRepository.findById(filmId)).thenReturn(Optional.of(film));
        when(filmRowMapper.toDto(film)).thenReturn(new FilmDto(film.getTitle()));

        FilmDto result = filmService.findById(filmId);

        assertNotNull(result);
        assertEquals(film.getTitle(), result.getTitle());
    }

    @Test
    void testFindByIdNotFound() {
        Long filmId = 1L;

        when(filmRepository.findById(filmId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> filmService.findById(filmId));
    }

    @Test
    void testFindAll() {
        List<Film> films = Collections.singletonList(new Film(1L, "Test Film"));

        when(filmRepository.findAll()).thenReturn(films);
        when(filmRowMapper.toDto(any(Film.class))).thenReturn(new FilmDto("Test Film"));

        List<FilmDto> result = filmService.findAll();

        assertEquals(1, result.size());
        assertEquals("Test Film", result.get(0).getTitle());
    }

    @Test
    void testCreateFilm() {
        FilmDto filmDto = new FilmDto("New Film");
        Film film = new Film();
        film.setTitle(filmDto.getTitle());

        when(filmRowMapper.toEntity(filmDto)).thenReturn(film);
        when(filmRowMapper.toDto(film)).thenReturn(filmDto);
        when(filmRepository.saveAndFlush(film)).thenReturn(film);

        FilmDto result = filmService.create(filmDto);

        assertNotNull(result);
        assertEquals(filmDto.getTitle(), result.getTitle());
    }

    @Test
    void testUpdateFilm() {
        Long id = 1L;
        FilmDto filmDto = new FilmDto("Updated Film");

        Film film = new Film();
        film.setId(id);
        film.setTitle("Film");

        Film updatedFilm = new Film();
        updatedFilm.setId(id);
        updatedFilm.setTitle(filmDto.getTitle());

        when(filmRepository.findById(id)).thenReturn(Optional.of(film));
        when(filmRowMapper.toDto(updatedFilm)).thenReturn(filmDto);
        when(filmRepository.saveAndFlush(any(Film.class))).thenReturn(updatedFilm);

        FilmService filmService = new FilmServiceImpl(filmRepository, filmRowMapper);
        FilmDto result = filmService.update(filmDto, id);

        assertNotNull(result);
        assertEquals(filmDto.getTitle(), result.getTitle());
    }

    @Test
    void testDelete() {
        Long id = 17L;

        filmService.delete(id);

        verify(filmRepository, times(1)).deleteById(id);
    }
}
