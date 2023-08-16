package org.example.servlet;

import org.example.entity.FilmCategory;
import org.example.repository.FilmCategoryRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(value = "/filmsByCategory", loadOnStartup = 1)
public class ServletFilmsByCategory extends HttpServlet {

    private FilmCategoryRepository filmCategoryRepository;

    @Override
    public void init() {
        filmCategoryRepository = new FilmCategoryRepository();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");

        String categoryName = request.getParameter("name");
        if (categoryName != null) {
            List<FilmCategory> filmsByCategory = filmCategoryRepository.findAllFilmsByCategory(categoryName);

            StringBuilder htmlResponse = new StringBuilder();
            htmlResponse.append("<html><body>");
            for (FilmCategory filmCategory : filmsByCategory) {
                htmlResponse.append("<p>Title: ").append(filmCategory.getTitle()).append("</p>");
                htmlResponse.append("<p>Language ID: ").append(filmCategory.getLanguageId()).append("</p>");
            }
            htmlResponse.append("</body></html>");

            response.getWriter().println(htmlResponse);
        } else {
            response.getWriter().println("Please provide a valid category name.");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // TODO document why this method is empty
    }
}
