package org.example.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.entity.Category;
import org.example.repository.CategoryRepository;
import org.example.repository.CategoryRepositoryImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

@WebServlet(value = "/categories", loadOnStartup = 1)
public class ServletCategories extends HttpServlet {

    private CategoryRepository categoryRepository;

    private ObjectMapper objectMapper;

    @Override
    public void init() {
        categoryRepository = new CategoryRepositoryImpl();
        objectMapper = new ObjectMapper();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        response.setContentType("application/json;charset=UTF-8");

        String categoryId = request.getParameter("id");
        if (categoryId != null) {
            try {
                long id = Long.parseLong(categoryId);
                Category category = categoryRepository.findById(id);

                String jsonResponse;
                jsonResponse = objectMapper.writeValueAsString(Objects.requireNonNullElse(category, "Category not found."));
                response.getWriter().println(jsonResponse);
            } catch (NumberFormatException e) {
                String jsonResponse = objectMapper.writeValueAsString("Invalid category ID.");
                response.getWriter().println(jsonResponse);
            }
        } else {
            List<Category> categories = categoryRepository.findAll();

            String jsonResponse = objectMapper.writeValueAsString(categories);
            response.getWriter().println(jsonResponse);
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
                boolean deleted = categoryRepository.delete(id);

                String jsonResponse;
                if (deleted) {
                    jsonResponse = objectMapper.writeValueAsString("Category deleted successfully.");
                } else {
                    jsonResponse = objectMapper.writeValueAsString("Category not found or could not be deleted.");
                }
                response.getWriter().println(jsonResponse);
            } catch (NumberFormatException e) {
                String jsonResponse = objectMapper.writeValueAsString("Invalid category ID.");
                response.getWriter().println(jsonResponse);
            }
        } else {
            String jsonResponse = objectMapper.writeValueAsString("Please provide a valid category ID to delete.");
            response.getWriter().println(jsonResponse);
        }
    }
}

