package org.example.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.entity.Film;
import org.example.service.CategoryService;
import org.example.service.FilmService;
import org.example.service.impl.CategoryServiceImpl;
import org.example.service.impl.FilmServiceImpl;
import org.example.util.BaseConnection;
import org.example.repository.BaseRepository;
import org.example.repository.CategoryRepository;
import org.example.repository.FilmRepository;
import org.example.repository.impl.CategoryRepositoryImpl;
import org.example.repository.impl.FilmRepositoryImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(value = "/films", loadOnStartup = 1)
public class ServletFilms extends HttpServlet {

    private CategoryService categoryService;

    private FilmService filmService;

    private ObjectMapper objectMapper;

    @Override
    public void init() {
        BaseConnection baseConnection = new BaseConnection(new BaseRepository("application.properties"));

        CategoryRepository categoryRepository = new CategoryRepositoryImpl(baseConnection);
        categoryService = new CategoryServiceImpl(categoryRepository);

        FilmRepository filmRepository = new FilmRepositoryImpl(baseConnection);
        filmService = new FilmServiceImpl(filmRepository);

        objectMapper = new ObjectMapper();
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        response.setContentType("application/json;charset=UTF-8");

        String filmId = request.getParameter("id");
        if (filmId != null) {
            try {
                long id = Long.parseLong(filmId);
                Film film = filmService.findById(id);

                String json;
                if (film != null) {
                    json = objectMapper.writeValueAsString(film);
                } else {
                    json = "Film not found.";
                }
                response.getWriter().println(json);
            } catch (NumberFormatException e) {
                String json = "Invalid film ID.";
                response.getWriter().println(json);
            }
        } else {
            List<Film> films = filmService.findAll();

            String json = objectMapper.writeValueAsString(films);
            response.getWriter().println(json);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        response.setContentType("application/json;charset=UTF-8");

        String filmId = request.getParameter("id");

        if (filmId != null) {
            try {
                long id = Long.parseLong(filmId);
                boolean deleted = filmService.delete(id);

                String jsonResponse;
                if (deleted) {
                    jsonResponse = objectMapper.writeValueAsString("Film deleted successfully.");
                } else {
                    jsonResponse = objectMapper.writeValueAsString("Film not found or could not be deleted.");
                }
                response.getWriter().println(jsonResponse);
            } catch (NumberFormatException e) {
                String jsonResponse = objectMapper.writeValueAsString("Invalid film ID.");
                response.getWriter().println(jsonResponse);
            }
        } else {
            String jsonResponse = objectMapper.writeValueAsString("Please provide a valid film ID to delete.");
            response.getWriter().println(jsonResponse);
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");

        String filmIdParameter = request.getParameter("id");
        if (filmIdParameter == null) {
            String jsonResponse = objectMapper.writeValueAsString("Please provide a valid film ID to update.");
            response.getWriter().println(jsonResponse);
            return;
        }

        try {
            long id = Long.parseLong(filmIdParameter);
            Film existingFilm = filmService.findById(id);

            if (existingFilm == null) {
                String jsonResponse = objectMapper.writeValueAsString("Film not found.");
                response.getWriter().println(jsonResponse);
                return;
            }

            String newTitle = request.getParameter("title");
            String newLanguageId = request.getParameter("languageId");

            if (newTitle == null && newLanguageId == null) {
                String jsonResponse = objectMapper.writeValueAsString("Please provide a new title or languageId.");
                response.getWriter().println(jsonResponse);
                return;
            }

            if (newTitle != null) {
                existingFilm.setTitle(newTitle);
            }

            if (newLanguageId != null) {
                try {
                    existingFilm.setLanguageId(Long.parseLong(newLanguageId));
                } catch (NumberFormatException e) {
                    String jsonResponse = objectMapper.writeValueAsString("Invalid languageId format.");
                    response.getWriter().println(jsonResponse);
                    return;
                }
            }

            Film updatedFilm = filmService.update(existingFilm);

            if (updatedFilm != null) {
                String jsonResponse = objectMapper.writeValueAsString("Film updated successfully.");
                response.getWriter().println(jsonResponse);
            } else {
                String jsonResponse = objectMapper.writeValueAsString("Failed to update film.");
                response.getWriter().println(jsonResponse);
            }
        } catch (NumberFormatException e) {
            String jsonResponse = objectMapper.writeValueAsString("Invalid film ID.");
            response.getWriter().println(jsonResponse);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        ObjectMapper objectMapper = new ObjectMapper();

        String title = request.getParameter("title");
        long languageId;
        try {
            languageId = Long.parseLong(request.getParameter("languageId"));
        } catch (NumberFormatException e) {
            String jsonResponse = objectMapper.writeValueAsString("Invalid languageId format.");
            response.getWriter().println(jsonResponse);
            return;
        }

        Film newFilm = new Film();
        newFilm.setTitle(title);
        newFilm.setLanguageId(languageId);

        Film createdFilm = filmService.create(newFilm);

        if (createdFilm != null) {
            String jsonResponse = objectMapper.writeValueAsString("Film created successfully.");
            response.getWriter().println(jsonResponse);
        } else {
            String jsonResponse = objectMapper.writeValueAsString("Failed to create film.");
            response.getWriter().println(jsonResponse);
        }
    }
}