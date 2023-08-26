package org.example.servlet;

import org.example.entity.Film;
import org.example.service.impl.FilmServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ServletFilmsTest {

    @Mock
    private FilmServiceImpl filmService;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @InjectMocks
    private ServletFilms servlet;

    private PrintWriter writer;

    @BeforeEach
    void setUp() {
        servlet.init();
        servlet.setFilmService(filmService);
        StringWriter stringWriter = new StringWriter();
        writer = new PrintWriter(stringWriter);
    }

    @Test
    void testFindById() throws Exception {
        Long filmId = 1L;
        Film testFilm = new Film();
        testFilm.setId(filmId);
        testFilm.setTitle("Test Film");

        when(request.getParameter("id")).thenReturn("1");
        when(filmService.findById(filmId)).thenReturn(testFilm);
        when(response.getWriter()).thenReturn(writer);

        servlet.doGet(request, response);

        Film film = filmService.findById(filmId);

        assertEquals(filmId, film.getId());
        assertEquals("Test Film", film.getTitle());
    }

    @Test
    void testFindAll() throws Exception {
        List<Film> films = new ArrayList<>();
        films.add(new Film(1L, "Film 1"));
        films.add(new Film(2L, "Film 2"));
        when(filmService.findAll()).thenReturn(films);
        when(response.getWriter()).thenReturn(writer);

        servlet.doGet(request, response);
        List<Film> testFilms = filmService.findAll();

        assertEquals(films.size(), testFilms.size());
    }

    @Test
    void testInvalidFilmId() throws Exception {
        when(request.getParameter("id")).thenReturn("invalid_id");

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        servlet.doGet(request, response);

        assertTrue(stringWriter.toString().contains("Invalid film ID"));
    }

    @Test
    void testUpdateValidFilm() throws Exception {
        Long filmId = 1L;

        Film film = new Film();
        film.setId(filmId);
        film.setTitle("Updated Film Title");
        when(filmService.update(film)).thenReturn(film);

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        servlet.doPut(request, response);

        Film updatedFilm = filmService.update(film);

        assertEquals(filmId, updatedFilm.getId());
        assertEquals("Updated Film Title", updatedFilm.getTitle());
    }

    @Test
    void testCreateValidFilm() throws Exception {
        Film newFilm = new Film();
        newFilm.setTitle("New Film");
        newFilm.setLanguageId(3L);
        when(filmService.create(newFilm)).thenReturn(newFilm);

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        servlet.doPost(request, response);

        Film createdFilm = filmService.create(newFilm);

        assertEquals("New Film", createdFilm.getTitle());
    }
}
