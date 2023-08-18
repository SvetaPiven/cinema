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

//    public List<Category> getCategoriesForFilm(Long filmId, Connection connection) {
//        final String categoryQuery = "SELECT c.id, c.name FROM category c " +
//                "JOIN l_films_category fc ON fc.category_id = c.id " +
//                "JOIN film f ON f.id = fc.films_id " +
//                "WHERE f.id = ?";
//        List<Category> categories = new ArrayList<>();
//
//        try (PreparedStatement preparedStatement = connection.prepareStatement(categoryQuery)
//        ) {
//            preparedStatement.setLong(1, filmId);
//            try (ResultSet rs = preparedStatement.executeQuery()) {
//                while (rs.next()) {
//                    Category category = new Category();
//                    category.setId(rs.getLong("id"));
//                    category.setName(rs.getString("name"));
//                    categories.add(category);
//                }
//            }
//        } catch (SQLException e) {
//            System.err.println(e.getMessage());
//            throw new RuntimeException("SQL Issues!");
//        }
//        return categories;
//    }

}
