package org.example.repository;

import org.example.TestConfig;
import org.example.entity.Language;
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
class LanguageRepositoryTests {

    private static final PostgreSQLContainer<?> postgreSQLContainer =
            new PostgreSQLContainer<>("postgres:15.3")
                    .withDatabaseName("testcinema")
                    .withUsername("development")
                    .withPassword("dev")
                    .withInitScript("migration/script.sql");
    @Autowired
    private LanguageRepository languageRepository;

    @BeforeAll
    static void beforeAll() {
        postgreSQLContainer.start();
    }

    @Test
    void testFindById() {
        Long languageId = 10L;
        Optional<Language> languageOptional = languageRepository.findById(languageId);

        assertTrue(languageOptional.isPresent());
        assertEquals(languageId, languageOptional.get().getId());
    }

    @Test
    void testFindAll() {
        List<Language> languages = languageRepository.findAll();

        assertNotNull(languages);
    }

    @Test
    void testDelete() {
        Long id = 5L;
        languageRepository.deleteById(id);

        Optional<Language> deletedLanguage = languageRepository.findById(id);
        assertFalse(deletedLanguage.isPresent());
    }

    @Test
    void testSave() {
        Language language = new Language();
        language.setName("Test Language");

        Language savedLanguage = languageRepository.save(language);

        assertNotNull(savedLanguage.getId());
        assertEquals(language.getName(), savedLanguage.getName());
    }

    @Test
    void testUpdate() {
        Language language = new Language();
        language.setName("Test Language");

        Language savedLanguage = languageRepository.save(language);

        assertNotNull(savedLanguage.getId());
        assertEquals(language.getName(), savedLanguage.getName());

        savedLanguage.setName("Updated Language");
        Language updatedLanguage = languageRepository.saveAndFlush(savedLanguage);

        assertEquals(savedLanguage.getId(), updatedLanguage.getId());
        assertEquals(savedLanguage.getName(), updatedLanguage.getName());
    }
}