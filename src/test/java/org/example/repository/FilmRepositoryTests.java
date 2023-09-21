package org.example.repository;

import org.example.TestConfig;
import org.example.entity.Film;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.web.WebAppConfiguration;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@WebAppConfiguration
@Testcontainers
@SpringJUnitConfig(TestConfig.class)
@Import(TestConfig.class)
@TestPropertySource("classpath:test.properties")
class FilmRepositoryTests {

    private static final PostgreSQLContainer<?> postgreSQLContainer =
            new PostgreSQLContainer<>("postgres:15.3")
                    .withDatabaseName("testcinema")
                    .withUsername("development")
                    .withPassword("dev")
                    .withInitScript("migration/script.sql");
    @Autowired
    private FilmRepository filmRepository;

    @BeforeAll
    static void beforeAll() {
        postgreSQLContainer.start();
    }

    @Test
    void testFindById() {
        Long filmId = 10L;
        Optional<Film> filmOptional = filmRepository.findById(filmId);

        assertTrue(filmOptional.isPresent());
        assertEquals(filmId, filmOptional.get().getId());
    }

    @Test
    void testFindAll() {
        List<Film> films = filmRepository.findAll();

        assertNotNull(films);
    }

    @Test
    void testDelete() {
        Long id = 5L;
        filmRepository.deleteById(id);

        Optional<Film> deletedFilm = filmRepository.findById(id);
        assertFalse(deletedFilm.isPresent());
    }

    @Test
    void testSave() {
        Film film = new Film();
        film.setTitle("Test Film");

        Film savedFilm = filmRepository.save(film);

        assertNotNull(savedFilm.getId());
        assertEquals(film.getTitle(), savedFilm.getTitle());
    }

    @Test
    void testUpdate() {
        Film film = new Film();
        film.setTitle("Test Film");

        Film savedFilm = filmRepository.save(film);

        assertNotNull(savedFilm.getId());
        assertEquals(film.getTitle(), savedFilm.getTitle());

        savedFilm.setTitle("Updated Film");
        Film updatedFilm = filmRepository.saveAndFlush(savedFilm);

        assertEquals(savedFilm.getId(), updatedFilm.getId());
        assertEquals(savedFilm.getTitle(), updatedFilm.getTitle());
    }
}