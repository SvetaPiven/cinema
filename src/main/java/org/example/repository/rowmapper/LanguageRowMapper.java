package org.example.repository.rowmapper;

import org.example.entity.Category;
import org.example.entity.Film;
import org.example.entity.Language;
import org.example.repository.impl.LanguageRepositoryImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LanguageRowMapper {

    private final FilmRowMapper filmRowMapper;

    private final Connection connection;

    public LanguageRowMapper(FilmRowMapper filmRowMapper, Connection connection) {
        this.filmRowMapper = filmRowMapper;
        this.connection = connection;
    }
    public Language processResultSetLanguage(ResultSet rs) {

        Language language;

        try {
            language = new Language();
            language.setId(rs.getLong(LanguageRepositoryImpl.ID));
            language.setName(rs.getString(LanguageRepositoryImpl.NAME));
            List<Film> films = getFilmsForLanguages(language.getId(), connection);

            for (Film film : films) {
                List<Category> categories = filmRowMapper.getCategoriesForFilm(film.getId(), connection);
                film.setCategories(categories);
            }
            language.setFilms(films);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return language;
    }

    private List<Film> getFilmsForLanguages(Long languageId, Connection connection) {
        final String languageQuery = "SELECT f.id, f.title FROM language l " +
                "JOIN film f ON f.language_id = l.id " +
                "WHERE l.id = ?";
        List<Film> films = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(languageQuery)
        ) {
            preparedStatement.setLong(1, languageId);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    Film film = new Film();
                    film.setId(rs.getLong("id"));
                    film.setTitle(rs.getString("title"));
                    films.add(film);
                }
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException("SQL Issues!");
        }
        return films;
    }
}
