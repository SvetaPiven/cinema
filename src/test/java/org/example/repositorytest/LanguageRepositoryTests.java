package org.example.repositorytest;

import org.example.entity.Film;
import org.example.entity.Language;
import repository.BaseRepository;
import repository.FilmRepository;
import repository.LanguageRepository;
import repository.impl.FilmRepositoryImpl;
import repository.impl.LanguageRepositoryImpl;
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

class LanguageRepositoryTests {

    private static final PostgreSQLContainer<?> postgreSQLContainer =
            new PostgreSQLContainer<>("postgres:15.3")
                    .withDatabaseName("testcinema")
                    .withUsername("development")
                    .withPassword("dev")
                    .withInitScript("migration/script.sql");

    private static LanguageRepository languageRepository;

    private static FilmRepository filmRepository;

    @BeforeAll
    public static void setUpContainer() {
        postgreSQLContainer.start();

        BaseRepository baseRepository = new BaseRepository("test.properties");
        baseRepository.setDatabaseUrl(postgreSQLContainer.getJdbcUrl());

        languageRepository = new LanguageRepositoryImpl(new BaseConnection(baseRepository));
        filmRepository = new FilmRepositoryImpl(new BaseConnection(baseRepository));
    }

    @AfterAll
    static void cleanUp() {
        postgreSQLContainer.stop();
    }

    @Test
    void testFindAll() {
        List<Language> languages = languageRepository.findAll();

        assertNotNull(languages);
    }

    @Test
    void testFindById() {
        Long languageId = 3L;
        Language language = languageRepository.findById(languageId);

        assertNotNull(language);
        assertEquals(languageId, language.getId());
    }

    @Test
    void testDeleteLanguageWithAssociatedFilms() {
        List<Film> filmsWithLanguage = filmRepository.findAll();

        for (Film film : filmsWithLanguage) {
            filmRepository.delete(film.getId());
        }

        Long languageIdToDelete = 2L;
        boolean result = languageRepository.delete(languageIdToDelete);

        assertTrue(result);

        Language deletedLanguage = languageRepository.findById(languageIdToDelete);
        assertNull(deletedLanguage);
    }
}

