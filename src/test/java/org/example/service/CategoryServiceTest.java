package org.example.service;

import org.example.entity.Category;
import org.example.entity.Film;
import org.example.repository.CategoryRepository;
import org.example.service.impl.CategoryServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    private CategoryService categoryService;

    @BeforeEach
    public void setUp() {
        categoryService = new CategoryServiceImpl(categoryRepository);
    }

    @Test
    void testFindById() {
        Long categoryId = 1L;
        Category test = new Category();
        test.setId(categoryId);
        test.setName("New Category");

        when(categoryRepository.findById(categoryId)).thenReturn(test);

        Category category = categoryService.findById(categoryId);

        assertEquals(categoryId, category.getId());
        assertEquals("New Category", category.getName());

    }

    @Test
    void testFindAll() {
        Film film = new Film();
        List<Category> test = new ArrayList<>();
        test.add(new Category(1L, "1 Category", Collections.singletonList(film)));
        test.add(new Category(2L, "2 Category", Collections.singletonList(film)));
        test.add(new Category(3L, "3 Category", Collections.singletonList(film)));

        when(categoryRepository.findAll()).thenReturn(test);

        List<Category> categories = categoryService.findAll();

        assertEquals(test.size(), categories.size());
    }

    @Test
    void testDelete() {
        Long categoryIdToDelete = 1L;

        when(categoryRepository.delete(categoryIdToDelete)).thenReturn(true);

        boolean result = categoryService.delete(categoryIdToDelete);

        assertTrue(result);
    }
}
