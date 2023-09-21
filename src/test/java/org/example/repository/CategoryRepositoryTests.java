package org.example.repository;

import org.example.TestConfig;
import org.example.entity.Category;
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
class CategoryRepositoryTests {

    private static final PostgreSQLContainer<?> postgreSQLContainer =
            new PostgreSQLContainer<>("postgres:15.3")
                    .withDatabaseName("testcinema")
                    .withUsername("development")
                    .withPassword("dev")
                    .withInitScript("migration/script.sql");
    @Autowired
    private CategoryRepository categoryRepository;

    @BeforeAll
    static void beforeAll() {
        postgreSQLContainer.start();
    }

    @Test
    void testFindById() {
        Long categoryId = 10L;
        Optional<Category> categoryOptional = categoryRepository.findById(categoryId);

        assertTrue(categoryOptional.isPresent());
        assertEquals(categoryId, categoryOptional.get().getId());
    }

    @Test
    void testFindAll() {
        List<Category> categories = categoryRepository.findAll();

        assertNotNull(categories);
    }

    @Test
    void testDelete() {
        Long id = 5L;
        categoryRepository.deleteById(id);

        Optional<Category> deletedCategory = categoryRepository.findById(id);
        assertFalse(deletedCategory.isPresent());
    }

    @Test
    void testSave() {
        Category category = new Category();
        category.setName("Test Category");

        Category savedCategory = categoryRepository.save(category);

        assertNotNull(savedCategory.getId());
        assertEquals(category.getName(), savedCategory.getName());
    }

    @Test
    void testUpdate() {
        Category category = new Category();
        category.setName("Test Category");

        Category savedCategory = categoryRepository.save(category);

        assertNotNull(savedCategory.getId());
        assertEquals(category.getName(), savedCategory.getName());

        savedCategory.setName("Updated Category");
        Category updatedCategory = categoryRepository.saveAndFlush(savedCategory);

        assertEquals(savedCategory.getId(), updatedCategory.getId());
        assertEquals(savedCategory.getName(), updatedCategory.getName());
    }
}