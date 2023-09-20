package org.example.service;

import org.example.entity.Category;
import org.example.entity.dto.CategoryDto;
import org.example.mapper.CategoryRowMapper;
import org.example.repository.CategoryRepository;
import org.example.service.impl.CategoryServiceImpl;
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
class CategoryServiceImplTest {

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private CategoryRowMapper categoryRowMapper;

    private CategoryService categoryService;

    @BeforeEach
    void setUp() {
        categoryService = new CategoryServiceImpl(categoryRepository, categoryRowMapper);
    }

    @Test
    void testFindById() {
        Long categoryId = 1L;
        Category category = new Category();
        category.setId(categoryId);
        category.setName("Test Category");

        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));
        when(categoryRowMapper.toDto(category)).thenReturn(new CategoryDto(category.getName()));

        CategoryDto result = categoryService.findById(categoryId);

        assertNotNull(result);
        assertEquals(category.getName(), result.getName());
    }

    @Test
    void testFindByIdNotFound() {
        Long categoryId = 1L;

        when(categoryRepository.findById(categoryId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> categoryService.findById(categoryId));
    }

    @Test
    void testFindAll() {
        List<Category> categories = Collections.singletonList(new Category(1L, "Test Category"));

        when(categoryRepository.findAll()).thenReturn(categories);
        when(categoryRowMapper.toDto(any(Category.class))).thenReturn(new CategoryDto("Test Category"));

        List<CategoryDto> result = categoryService.findAll();

        assertEquals(1, result.size());
        assertEquals("Test Category", result.get(0).getName());
    }

    @Test
    void testCreateCategory() {
        CategoryDto categoryDto = new CategoryDto("New Category");
        Category category = new Category();
        category.setName(categoryDto.getName());

        when(categoryRowMapper.toEntity(categoryDto)).thenReturn(category);
        when(categoryRowMapper.toDto(category)).thenReturn(categoryDto);
        when(categoryRepository.saveAndFlush(category)).thenReturn(category);

        CategoryDto result = categoryService.create(categoryDto);

        assertNotNull(result);
        assertEquals(categoryDto.getName(), result.getName());
    }


    @Test
    void testUpdateCategory() {
        Long id = 1L;
        CategoryDto categoryDto = new CategoryDto("Updated Category");

        Category category = new Category();
        category.setId(id);
        category.setName("Category");

        Category updatedCategory = new Category();
        updatedCategory.setId(id);
        updatedCategory.setName(categoryDto.getName());

        when(categoryRepository.findById(id)).thenReturn(Optional.of(category));
        when(categoryRowMapper.toDto(updatedCategory)).thenReturn(categoryDto);
        when(categoryRepository.saveAndFlush(any(Category.class))).thenReturn(updatedCategory);

        CategoryService categoryService = new CategoryServiceImpl(categoryRepository, categoryRowMapper);
        CategoryDto result = categoryService.update(categoryDto, id);

        assertNotNull(result);
        assertEquals(categoryDto.getName(), result.getName());
    }

    @Test
    void testDelete() {
        Long id = 17L;

        categoryService.delete(id);

        verify(categoryRepository, times(1)).deleteById(id);
    }
}
