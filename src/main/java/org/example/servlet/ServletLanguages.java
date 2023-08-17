package org.example.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.entity.Language;
import org.example.repository.LanguageRepository;
import org.example.repository.LanguageRepositoryImpl;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(value = "/languages", loadOnStartup = 1)
public class ServletLanguages extends HttpServlet {

    private LanguageRepository languageRepository;

    private ObjectMapper objectMapper;

    @Override
    public void init() {
        languageRepository = new LanguageRepositoryImpl();
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
                Language language = languageRepository.findById(id);

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
            List<Language> languages = languageRepository.findAll();

            String json = objectMapper.writeValueAsString(languages);
            response.getWriter().println(json);
        }
    }
}