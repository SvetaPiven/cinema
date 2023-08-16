package org.example.servlet;

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

@WebServlet(value = "/categories", loadOnStartup = 1)
public class ServletCategories extends HttpServlet {

    private CategoryRepository categoryRepository;

    @Override
    public void init() {
        categoryRepository = new CategoryRepositoryImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        response.setContentType("text/html");

        String categoryIdParameter = request.getParameter("id");
        if (categoryIdParameter != null) {
            try {
                long id = Long.parseLong(categoryIdParameter);
                Category category = categoryRepository.findOne(id);

                if (category != null) {
                    String htmlResponse = "<html><body>" +
                            "<p>ID: " + category.getId() + "</p>" +
                            "<p>Name: " + category.getName() + "</p>" +
                            "</body></html>";

                    response.getWriter().println(htmlResponse);
                } else {
                    response.getWriter().println("Category not found.");
                }
            } catch (NumberFormatException e) {
                response.getWriter().println("Invalid category ID.");
            }
        } else {
            List<Category> categories = categoryRepository.findAll();

            StringBuilder htmlResponse = new StringBuilder();
            htmlResponse.append("<html><body>");
            for (Category category : categories) {
                htmlResponse.append("<p>ID: ").append(category.getId()).append("</p>");
                htmlResponse.append("<p>Name: ").append(category.getName()).append("</p>");
            }
            htmlResponse.append("</body></html>");

            response.getWriter().println(htmlResponse);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        response.setContentType("text/html");

        String categoryId = request.getParameter("id");
        if (categoryId != null) {
            try {
                long id = Long.parseLong(categoryId);
                boolean deleted = categoryRepository.delete(id);

                if (deleted) {
                    response.getWriter().println("Category deleted successfully.");
                } else {
                    response.getWriter().println("Category not found or could not be deleted.");
                }
            } catch (NumberFormatException e) {
                response.getWriter().println("Invalid category ID.");
            }
        } else {
            response.getWriter().println("Please provide a valid category ID to delete.");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // TODO document why this method is empty
    }
}
