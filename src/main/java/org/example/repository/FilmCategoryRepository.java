package org.example.repository;

import org.example.entity.FilmCategory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FilmCategoryRepository extends BaseRepository {

    public static final String TITLE = "title";

    public static final String LANGUAGE_ID = "language_id";

    private FilmCategory parseResultSet(ResultSet rs) {

        FilmCategory filmCategory;

        try {
            filmCategory = new FilmCategory();
            filmCategory.setTitle(rs.getString(TITLE));
            filmCategory.setLanguageId(rs.getLong(LANGUAGE_ID));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return filmCategory;
    }

    public List<FilmCategory> findAllFilmsByCategory(String name) {
        final String findAllFilmsByCategory = "select f.title, f.language_id from film f " +
                "join l_films_category fc on fc.films_id = f.id " +
                "join category c on c.id = fc.category_id " +
                "where c.name = ?";

        List<FilmCategory> result = new ArrayList<>();

        registerDriver();
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(findAllFilmsByCategory)
        ) {
            statement.setString(1, name);
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    result.add(parseResultSet(rs));
                }
            }
            return result;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException("SQL Issues!");
        }
    }
}
