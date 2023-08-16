package org.example.servlet;

import org.example.entity.Language;
import org.example.repository.LanguageRepository;
import org.example.repository.LanguageRepositoryImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(value = "/languages", loadOnStartup = 1)
public class ServletLanguages extends HttpServlet {

    private LanguageRepository languageRepository;

    @Override
    public void init() {
        languageRepository = new LanguageRepositoryImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        response.setContentType("text/html");

        String languageIdParameter = request.getParameter("id");
        if (languageIdParameter != null) {
            try {
                long id = Long.parseLong(languageIdParameter);
                Language language = languageRepository.findOne(id);

                if (language != null) {
                    String htmlResponse = "<html><body>" +
                            "<p>ID: " + language.getId() + "</p>" +
                            "<p>Name: " + language.getName() + "</p>" +
                            "</body></html>";

                    response.getWriter().println(htmlResponse);
                } else {
                    response.getWriter().println("Language not found.");
                }
            } catch (NumberFormatException e) {
                response.getWriter().println("Invalid language ID.");
            }
        } else {
            List<Language> languages = languageRepository.findAll();

            StringBuilder htmlResponse = new StringBuilder();
            htmlResponse.append("<html><body>");
            for (Language language : languages) {
                htmlResponse.append("<p>ID: ").append(language.getId()).append("</p>");
                htmlResponse.append("<p>Name: ").append(language.getName()).append("</p>");
            }
            htmlResponse.append("</body></html>");

            response.getWriter().println(htmlResponse);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        response.setContentType("text/html");

        String languageId = request.getParameter("id");
        if (languageId != null) {
            try {
                long id = Long.parseLong(languageId);
                boolean deleted = languageRepository.delete(id);

                if (deleted) {
                    response.getWriter().println("Language deleted successfully.");
                } else {
                    response.getWriter().println("Language not found or could not be deleted.");
                }
            } catch (NumberFormatException e) {
                response.getWriter().println("Invalid language ID.");
            }
        } else {
            response.getWriter().println("Please provide a valid language ID to delete.");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // TODO document why this method is empty
    }
}
