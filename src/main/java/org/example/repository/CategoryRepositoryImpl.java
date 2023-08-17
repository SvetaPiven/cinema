package org.example.repository;

import org.example.entity.Category;
import org.example.entity.Film;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CategoryRepositoryImpl extends BaseRepository implements CategoryRepository {

    public static final String ID = "id";

    public static final String NAME = "name";

    @Override
    public Category findById(Long id) {
        final String findOneQuery = "select * from category where id = " + id;

        registerDriver();
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(findOneQuery)
        ) {
            if (rs.next()) {
                return parseResultSet(rs);
            } else {
                return null;
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException("SQL Issues!");
        }
    }

    @Override
    public List<Category> findAll() {
        final String findAllQuery = "select * from category order by id";

        List<Category> result = new ArrayList<>();

        registerDriver();
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(findAllQuery)
        ) {

            while (rs.next()) {
                result.add(parseResultSet(rs));
            }
            return result;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException("SQL Issues!");
        }
    }

    @Override
    public Category create(Category object) {
        return null;
    }

    @Override
    public Category update(Category object) {
        return null;
    }

    @Override
    public boolean delete(Long id) {
        final String deleteQuery = "delete from category where id = " + id;

        registerDriver();
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()
        ) {
            statement.executeUpdate(deleteQuery);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException("SQL Issues!");
        }
        return true;
    }

    private Category parseResultSet(ResultSet rs) {
        Category category;

        try {
            category = new Category();
            category.setId(rs.getLong(ID));
            category.setName(rs.getString(NAME));
            List<Film> films = getFilmsForCategory(category.getId());
                    category.setFilms(films);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return category;
    }

    private List<Film> getFilmsForCategory(Long categoryId) {
        final String filmsQuery = "SELECT f.id, f.title, f.language_id FROM film f " +
                "JOIN l_films_category fc ON fc.films_id = f.id " +
                "JOIN category c ON c.id = fc.category_id " +
                "WHERE c.id = ?";
        List<Film> films = new ArrayList<>();

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(filmsQuery)
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