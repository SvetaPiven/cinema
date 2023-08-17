package org.example.repository.rowmapper;

import org.example.entity.Category;
import org.example.entity.Film;
import org.example.repository.impl.CategoryRepositoryImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoryRowMapper {

    private final Connection connection;

    public CategoryRowMapper(Connection connection) {
        this.connection = connection;
    }

    public Category processResultSetCategory(ResultSet rs) {
        try {
            Category category = new Category();
            category.setId(rs.getLong(CategoryRepositoryImpl.ID));
            category.setName(rs.getString(CategoryRepositoryImpl.NAME));
            List<Film> films = getFilmsForCategory(category.getId(), connection);
            category.setFilms(films);
            return category;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private List<Film> getFilmsForCategory(Long categoryId, Connection connection) {
        final String filmsQuery = "SELECT f.id, f.title, f.language_id FROM film f " +
                "JOIN l_films_category fc ON fc.films_id = f.id " +
                "JOIN category c ON c.id = fc.category_id " +
                "WHERE c.id = ?";
        List<Film> films = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(filmsQuery)
        ) {
            preparedStatement.setLong(1, categoryId);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    Film film = new Film();
                    film.setId(rs.getLong("id"));
                    film.setTitle(rs.getString("title"));
                    film.setLanguageId(rs.getLong("language_id"));
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
