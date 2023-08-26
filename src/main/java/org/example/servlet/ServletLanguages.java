package org.example.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.entity.Language;
import repository.BaseRepository;
import repository.LanguageRepository;
import repository.impl.LanguageRepositoryImpl;
import org.example.service.LanguageService;
import org.example.service.impl.LanguageServiceImpl;
import org.example.util.BaseConnection;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(value = "/languages", loadOnStartup = 1)
public class ServletLanguages extends HttpServlet {

    private LanguageService languageService;

    private ObjectMapper objectMapper;

    @Override
    public void init() {
        BaseConnection baseConnection = new BaseConnection(new BaseRepository("application.properties"));
        LanguageRepository languageRepository = new LanguageRepositoryImpl(baseConnection);
        languageService = new LanguageServiceImpl(languageRepository);
        objectMapper = new ObjectMapper();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        response.setContentType("application/json;charset=UTF-8");

        String languageIdParameter = request.getParameter("id");
        if (languageIdParameter != null) {
            try {
                long id = Long.parseLong(languageIdParameter);
                Language language = languageService.findById(id);

                String json;
                if (language != null) {
                    json = objectMapper.writeValueAsString(language);
                } else {
                    json = "Language not found.";
                }
                response.getWriter().println(json);
            } catch (NumberFormatException e) {
                String json = "Invalid language ID.";
                response.getWriter().println(json);
            }
        } else {
            List<Language> languages = languageService.findAll();

            String json = objectMapper.writeValueAsString(languages);
            response.getWriter().println(json);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json;charset=UTF-8");

        String languageId = request.getParameter("id");
        if (languageId != null) {
            try {
                long id = Long.parseLong(languageId);
                boolean deleted = languageService.delete(id);

                if (deleted) {
                    objectMapper.writeValue(response.getWriter(), "Language deleted successfully.");
                } else {
                    objectMapper.writeValue(response.getWriter(), "Language not found or could not be deleted.");
                }
            } catch (NumberFormatException e) {
                objectMapper.writeValue(response.getWriter(), "Invalid language ID.");
            }
        } else {
            objectMapper.writeValue(response.getWriter(), "Please provide a valid language ID to delete.");
        }
    }
}