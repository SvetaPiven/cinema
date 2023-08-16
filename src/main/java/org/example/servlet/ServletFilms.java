package org.example.servlet;

import org.example.entity.Film;
import org.example.repository.FilmRepository;
import org.example.repository.FilmRepositoryImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(value = "/films", loadOnStartup = 1)
public class ServletFilms extends HttpServlet {

    private FilmRepository filmRepository;

    @Override
    public void init() {
        filmRepository = new FilmRepositoryImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        response.setContentType("text/html");

        String filmIdParameter = request.getParameter("id");
        if (filmIdParameter != null) {
            try {
                long id = Long.parseLong(filmIdParameter);
                Film film = filmRepository.findOne(id);

                if (film != null) {
                    String htmlResponse = "<html><body>" +
                            "<p>ID: " + film.getId() + "</p>" +
                            "<p>Title: " + film.getTitle() + "</p>" +
                            "<p>Language ID: " + film.getLanguageId() + "</p>" +
                            "</body></html>";

                    response.getWriter().println(htmlResponse);
                } else {
                    response.getWriter().println("Film not found.");
                }
            } catch (NumberFormatException e) {
                response.getWriter().println("Invalid film ID.");
            }
        } else {
            List<Film> films = filmRepository.findAll();

            StringBuilder htmlResponse = new StringBuilder();
            htmlResponse.append("<html><body>");
            for (Film film : films) {
                htmlResponse.append("<p>ID: ").append(film.getId()).append("</p>");
                htmlResponse.append("<p>Title: ").append(film.getTitle()).append("</p>");
                htmlResponse.append("<p>Language ID: ").append(film.getLanguageId()).append("</p>");
            }
            htmlResponse.append("</body></html>");

            response.getWriter().println(htmlResponse);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        response.setContentType("text/html");

        String filmId = request.getParameter("id");
        if (filmId != null) {
            try {
                long id = Long.parseLong(filmId);
                boolean deleted = filmRepository.delete(id);

                if (deleted) {
                    response.getWriter().println("Film deleted successfully.");
                } else {
                    response.getWriter().println("FIlm not found or could not be deleted.");
                }
            } catch (NumberFormatException e) {
                response.getWriter().println("Invalid film ID.");
            }
        } else {
            response.getWriter().println("Please provide a valid film ID to delete.");
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");

        String filmIdParameter = request.getParameter("id");
        if (filmIdParameter == null) {
            response.getWriter().println("Please provide a valid film ID to update.");
            return;
        }

        try {
            long id = Long.parseLong(filmIdParameter);
            Film existingFilm = filmRepository.findOne(id);

            if (existingFilm == null) {
                response.getWriter().println("Film not found.");
                return;
            }

            String newTitle = request.getParameter("title");
            String newLanguageId = request.getParameter("languageId");

            if (newTitle == null && newLanguageId == null) {
                response.getWriter().println("Please provide a new title or languageId.");
                return;
            }

            if (newTitle != null) {
                existingFilm.setTitle(newTitle);
            }

            if (newLanguageId != null) {
                try {
                    existingFilm.setLanguageId(Long.parseLong(newLanguageId));
                } catch (NumberFormatException e) {
                    response.getWriter().println("Invalid languageId format.");
                    return;
                }
            }

            Film updatedFilm = filmRepository.update(existingFilm);

            if (updatedFilm != null) {
                response.getWriter().println("Film updated successfully.");
            } else {
                response.getWriter().println("Failed to update film.");
            }
        } catch (NumberFormatException e) {
            response.getWriter().println("Invalid film ID.");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");

        String title = request.getParameter("title");
        long languageId = Long.parseLong(request.getParameter("languageId"));

        Film newFilm = new Film();
        newFilm.setTitle(title);
        newFilm.setLanguageId(languageId);

        Film createdFilm = filmRepository.create(newFilm);

        if (createdFilm != null) {
            response.getWriter().println("Film created successfully.");
        } else {
            response.getWriter().println("Failed to create film.");
        }
    }
}