package org.example;

import org.example.entity.Film;
import org.example.repository.BaseRepository;
import org.example.repository.FilmRepository;
import org.example.repository.impl.FilmRepositoryImpl;
import org.example.util.BaseConnection;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FilmRepositoryTests {

    private static final PostgreSQLContainer<?> postgreSQLContainer =
            new PostgreSQLContainer<>("postgres:15.3")
                    .withDatabaseName("testcinema")
                    .withUsername("development")
                    .withPassword("dev")
                    .withInitScript("migration/script.sql");

    private static FilmRepository filmRepository;

    @BeforeAll
    public static void setUpContainer() {
        postgreSQLContainer.start();

        BaseRepository baseRepository = new BaseRepository("test.properties");
        baseRepository.setDatabaseUrl(postgreSQLContainer.getJdbcUrl());

        filmRepository = new FilmRepositoryImpl(new BaseConnection(baseRepository));

    }

    @AfterAll
    static void cleanUp() {
        postgreSQLContainer.stop();
    }

    @Test
    void testFindAll() {
        List<Film> films = filmRepository.findAll();

        assertNotNull(films);
    }

    @Test
    void testFindById() {
        Long filmId = 5L;
        Film film = filmRepository.findById(filmId);

        assertNotNull(film);
        assertEquals(filmId, film.getId());
    }

    @Test
    void testDelete() {
        Film newFilm = new Film();
        newFilm.setTitle("New Film");
        newFilm.setLanguageId(1L);
        Film createdFilm = filmRepository.create(newFilm);

        assertNotNull(createdFilm);

        Long filmIdToDelete = createdFilm.getId();
        boolean result = filmRepository.delete(filmIdToDelete);

        assertTrue(result);

        Film deletedFilm = filmRepository.findById(filmIdToDelete);
        assertNull(deletedFilm);
    }

    @Test
    void testUpdate() {
        Film newFilm = new Film();
        newFilm.setTitle("New film");
        newFilm.setLanguageId(2L);
        Film createdFilm = filmRepository.create(newFilm);

        assertNotNull(createdFilm);

        createdFilm.setTitle("Updated New film");
        Film updatedFilm = filmRepository.update(createdFilm);

        assertNotNull(updatedFilm);
        assertEquals("Updated New film", updatedFilm.getTitle());
    }

    @Test
    void testCreate() {
        Film newFilm = new Film();
        newFilm.setTitle("New film");
        newFilm.setLanguageId(1L);

        Film createdFilm = filmRepository.create(newFilm);

        assertNotNull(createdFilm);
        assertNotNull(createdFilm.getId());

        assertEquals("New film", createdFilm.getTitle());
        assertEquals(1L, createdFilm.getLanguageId());

        Film film = filmRepository.findById(createdFilm.getId());
        assertNotNull(film);
        assertEquals(createdFilm.getId(), film.getId());
        assertEquals(createdFilm.getTitle(), film.getTitle());
        assertEquals(createdFilm.getLanguageId(), film.getLanguageId());
    }
}

