package org.example.servlet;

import org.example.entity.Category;
import org.example.service.impl.CategoryServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ServletCategoriesTest {

    @Mock
    private CategoryServiceImpl categoryService;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @InjectMocks
    private ServletCategories servlet;

    private PrintWriter writer;

    @BeforeEach
    void setUp() {
        servlet.init();
        StringWriter stringWriter = new StringWriter();
        writer = new PrintWriter(stringWriter);
    }

    @Test
    void testDeleteValidCategoryId() throws Exception {
        Long categoryIdToDelete = 1L;
        when(request.getParameter("id")).thenReturn("1");
        when(response.getWriter()).thenReturn(writer);
        when(categoryService.delete(categoryIdToDelete)).thenReturn(true);

        servlet.doDelete(request, response);
        boolean result = categoryService.delete(categoryIdToDelete);

        assertTrue(result);
    }

    @Test
    void testFindByIdValidCategoryId() throws Exception {
        Long categoryId = 1L;
        Category test = new Category();
        test.setId(categoryId);
        test.setName("New Category");

        when(request.getParameter("id")).thenReturn(null);
        when(categoryService.findById(categoryId)).thenReturn(test);
        when(response.getWriter()).thenReturn(writer);

        servlet.doGet(request, response);

        Category category = categoryService.findById(categoryId);

        assertEquals(categoryId, category.getId());
        assertEquals("New Category", category.getName());
    }


    @Test
    void testValidFindAll() throws Exception {
        List<Category> categories = new ArrayList<>();
        categories.add(new Category(1L, "Category 1"));
        categories.add(new Category(2L, "Category 2"));

        when(categoryService.findAll()).thenReturn(categories);
        when(response.getWriter()).thenReturn(writer);

        servlet.doGet(request, response);
        List<Category> test = categoryService.findAll();

        assertEquals(categories.size(), test.size());
    }

    @Test
    void testInvalidCategoryId() throws Exception {
        when(request.getParameter("id")).thenReturn("invalid_id");

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        servlet.doGet(request, response);

        assertTrue(stringWriter.toString().contains("Invalid category ID"));
    }
}
