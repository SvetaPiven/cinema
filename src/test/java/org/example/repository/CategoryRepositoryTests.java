package org.example.repository;

import org.example.entity.Category;
import org.example.repository.impl.CategoryRepositoryImpl;
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

class CategoryRepositoryTests {

    private static final PostgreSQLContainer<?> postgreSQLContainer =
            new PostgreSQLContainer<>("postgres:15.3")
                    .withDatabaseName("testcinema")
                    .withUsername("development")
                    .withPassword("dev")
                    .withInitScript("migration/script.sql");

    private static CategoryRepository categoryRepository;


    @BeforeAll
    public static void setUpContainer() {
        postgreSQLContainer.start();

        BaseRepository baseRepository = new BaseRepository("test.properties");
        baseRepository.setDatabaseUrl(postgreSQLContainer.getJdbcUrl());

        categoryRepository = new CategoryRepositoryImpl(new BaseConnection(baseRepository));

    }

    @AfterAll
    static void cleanUp() {
        postgreSQLContainer.stop();
    }

    @Test
    void testFindById() {
        Long categoryId = 10L;
        Category category = categoryRepository.findById(categoryId);

        assertNotNull(category);
        assertEquals(categoryId, category.getId());
    }

    @Test
    void testFindAll() {
        List<Category> categories = categoryRepository.findAll();

        assertNotNull(categories);
    }

    @Test
    void testDelete() {
        Long categoryIdToDelete = 2L;
        boolean result = categoryRepository.delete(categoryIdToDelete);

        assertTrue(result);

        Category deletedCategory = categoryRepository.findById(categoryIdToDelete);
        assertNull(deletedCategory);
    }
}