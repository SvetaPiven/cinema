package repository.impl;

import org.example.entity.Category;
import org.example.entity.Film;
import repository.CategoryRepository;
import repository.rowmapper.CategoryRowMapper;
import org.example.util.BaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CategoryRepositoryImpl implements CategoryRepository {

    public static final String ID = "id";

    public static final String NAME = "name";

    private final BaseConnection baseConnection;

    public CategoryRepositoryImpl(BaseConnection baseConnection) {
        this.baseConnection = baseConnection;
    }

    @Override
    public Category findById(Long id) {
        final String findOneQuery = "select * from category where id = " + id;

        baseConnection.registerDriver();
        try (Connection connection = baseConnection.getConnection();
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

        baseConnection.registerDriver();
        try (Connection connection = baseConnection.getConnection();
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
        final String query = "DELETE FROM category WHERE id = ?";
        final String linkQuery = "DELETE FROM l_films_category WHERE category_id = ?";

        baseConnection.registerDriver();
        try (Connection connection = baseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             PreparedStatement linkStatement = connection.prepareStatement(linkQuery)
        ) {
            linkStatement.setLong(1, id);
            linkStatement.executeUpdate();

            statement.setLong(1, id);
            statement.executeUpdate();

            return true;

        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException("SQL Issues!");
        }
    }

    private Category parseResultSet(ResultSet rs) {
        CategoryRowMapper rowMapper = new CategoryRowMapper(baseConnection.getConnection(), this);
        return rowMapper.processResultSetCategory(rs);
    }

    public List<Film> getFilmsForCategory(Long categoryId, Connection connection) {
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