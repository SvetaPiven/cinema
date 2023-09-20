package org.example.controller;

import org.example.entity.dto.CategoryDto;
import org.example.service.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class CategoriesControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private CategoriesController categoriesController;

    @Mock
    private CategoryService categoryService;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(categoriesController).build();
    }

    @Test
    void testFindAllCategories() throws Exception {
        List<CategoryDto> categories = Arrays.asList(
                new CategoryDto("Category 1"),
                new CategoryDto("Category 2")
        );

        when(categoryService.findAll()).thenReturn(categories);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/categories")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].name").value("Category 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[1].name").value("Category 2"));
    }

    @Test
    void testFindCategoryById() throws Exception {
        Long categoryId = 1L;
        CategoryDto categoryDto = new CategoryDto("Test Category");

        when(categoryService.findById(categoryId)).thenReturn(categoryDto);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/categories/{id}", categoryId)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Test Category"));

    }

    @Test
    void testCreateCategory() throws Exception {
        CategoryDto categoryDto = new CategoryDto("New Category");

        when(categoryService.create(any(CategoryDto.class))).thenReturn(categoryDto);


        mockMvc.perform(MockMvcRequestBuilders
                        .post("/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(categoryDto))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").exists());

    }

    @Test
    void testUpdateCategory() throws Exception {
        Long categoryId = 1L;
        CategoryDto categoryDto = new CategoryDto("Updated Category");

        when(categoryService.update(Mockito.any(CategoryDto.class), Mockito.eq(categoryId)))
                .thenReturn(categoryDto);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/categories/{id}", categoryId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(categoryDto))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Updated Category"));
    }

    @Test
    void testDeleteCategoryById() throws Exception {
        Long categoryId = 1L;

        mockMvc.perform(MockMvcRequestBuilders.delete("/categories/{id}", categoryId) )
                .andExpect(status().isOk());
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
