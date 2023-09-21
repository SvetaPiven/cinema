package org.example.repository;


import org.example.config.TestConfig;
import org.example.entity.Language;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.web.WebAppConfiguration;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
    private CategoryRepository categoryRepository;

    @DynamicPropertySource
    static void postgresProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
    }

    @Autowired
    private LanguageRepository languageRepository;

    @BeforeAll
    static void beforeAll() {
        postgreSQLContainer.start();
    }

    @Test
    void testFindById() {
        Long languageId = 1L;
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
    void testDeleteLanguage() {
        Language language = new Language();
        language.setName("Test Language");
        language = languageRepository.save(language);
        assertNotNull(language);

        languageRepository.deleteById(language.getId());

        assertTrue(languageRepository.findById(language.getId()).isEmpty());
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