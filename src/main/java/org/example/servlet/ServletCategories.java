package org.example.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.entity.Category;
import org.example.service.CategoryService;
import org.example.service.impl.CategoryServiceImpl;
import org.example.util.BaseConnection;
import repository.BaseRepository;
import repository.CategoryRepository;
import repository.impl.CategoryRepositoryImpl;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Objects;

@WebServlet(value = "/categories", loadOnStartup = 1)
public class ServletCategories extends HttpServlet {

    CategoryService categoryService;
    ObjectMapper objectMapper;

    @Override
    public void init() {
        BaseConnection baseConnection = new BaseConnection(new BaseRepository("application.properties"));
        CategoryRepository categoryRepository = new CategoryRepositoryImpl(baseConnection);
        categoryService = new CategoryServiceImpl(categoryRepository);
        objectMapper = new ObjectMapper();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        response.setContentType("application/json;charset=UTF-8");

        PrintWriter writer = response.getWriter();

        String categoryId = request.getParameter("id");
        if (categoryId != null) {
            try {
                long id = Long.parseLong(categoryId);
                Category category = categoryService.findById(id);

                objectMapper.writeValue(writer, Objects.requireNonNullElse(category, "Category not found."));
                objectMapper.writeValue(writer, category.getFilms());
            } catch (NumberFormatException e) {
                objectMapper.writeValue(writer, "Invalid category ID.");
            }
        } else {
            List<Category> categories = categoryService.findAll();
            objectMapper.writeValue(writer, categories);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        response.setContentType("application/json;charset=UTF-8");

        String categoryId = request.getParameter("id");
        if (categoryId != null) {
            try {
                long id = Long.parseLong(categoryId);
                boolean deleted = categoryService.delete(id);

                if (deleted) {
                    objectMapper.writeValue(response.getWriter(), "Category deleted successfully.");
                } else {
                    objectMapper.writeValue(response.getWriter(), "Category not found or could not be deleted.");
                }
            } catch (NumberFormatException e) {
                objectMapper.writeValue(response.getWriter(), "Invalid category ID.");
            }
        } else {
            objectMapper.writeValue(response.getWriter(), "Please provide a valid category ID to delete.");
        }
    }
}
