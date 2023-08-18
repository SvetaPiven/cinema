package org.example.repository.rowmapper;

import org.example.entity.Category;
import org.example.entity.Film;
import org.example.repository.impl.FilmRepositoryImpl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class FilmRowMapper {

    private final Connection connection;

    private final FilmRepositoryImpl filmRepository;

    public FilmRowMapper(Connection connection, FilmRepositoryImpl filmRepository) {
        this.connection = connection;
        this.filmRepository = filmRepository;
    }

    public Film processResultSetFilm(ResultSet rs) {

        Film film;

        try {
            film = new Film();
            film.setId(rs.getLong(FilmRepositoryImpl.ID));
            film.setTitle(rs.getString(FilmRepositoryImpl.TITLE));
            film.setLanguageId(rs.getLong(FilmRepositoryImpl.LANGUAGE_ID));
            List<Category> categories = filmRepository.getCategoriesForFilm(film.getId(), connection);
            film.setCategories(categories);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return film;
    }
}
