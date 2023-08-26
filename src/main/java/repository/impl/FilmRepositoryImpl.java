package repository.impl;

import org.example.entity.Category;
import org.example.entity.Film;
import repository.FilmRepository;
import repository.rowmapper.FilmRowMapper;
import org.example.util.BaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class FilmRepositoryImpl implements FilmRepository {

    public static final String ID = "id";

    public static final String TITLE = "title";

    public static final String LANGUAGE_ID = "language_id";

    private final BaseConnection baseConnection;

    public FilmRepositoryImpl(BaseConnection baseConnection) {
        this.baseConnection = baseConnection;
    }

    @Override
    public List<Film> findAll() {
        final String findAllQuery = "select * from film order by id ";

        List<Film> result = new ArrayList<>();

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

    private Film parseResultSet(ResultSet rs) {
        FilmRowMapper rowMapper = new FilmRowMapper(baseConnection.getConnection(), this);
        return rowMapper.processResultSetFilm(rs);
    }

    @Override
    public Film findById(Long id) {
        final String findByIdQuery = "select * from film where id = " + id;

        baseConnection.registerDriver();
        try (Connection connection = baseConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(findByIdQuery)
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
    public boolean delete(Long id) {
        final String query = "DELETE FROM film WHERE id = ?";
        final String linkQuery = "DELETE FROM l_films_category WHERE films_id = ?";

        baseConnection.registerDriver();
        try (Connection connection = baseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(linkQuery);
             PreparedStatement linkStatement = connection.prepareStatement(query)
        ) {
            statement.setLong(1, id);
            statement.executeUpdate();

            linkStatement.setLong(1, id);
            linkStatement.executeUpdate();

            return true;

        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException("SQL Issues!");
        }
    }

    @Override
    public Film update(Film film) {
        final String updateQuery = "update film set title = ?, language_id = ? where id = ?";

        baseConnection.registerDriver();
        try (Connection connection = baseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)
        ) {
            preparedStatement.setString(1, film.getTitle());
            preparedStatement.setLong(2, film.getLanguageId());
            preparedStatement.setLong(3, film.getId());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException("SQL Issues!");
        }
        return film;
    }

    @Override
    public Film create(Film film) {
        final String insertQuery = "insert into film (title, language_id) values (?, ?)";

        baseConnection.registerDriver();
        try (Connection connection = baseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS)
        ) {
            preparedStatement.setString(1, film.getTitle());
            preparedStatement.setLong(2, film.getLanguageId());

            int rowsInserted = preparedStatement.executeUpdate();
            if (rowsInserted > 0) {
                ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    long id = generatedKeys.getLong(1);
                    film.setId(id);
                    return film;
                }
            }
            return null;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException("SQL Issues!");
        }
    }

    public List<Category> getCategoriesForFilm(Long filmId, Connection connection) {
        final String categoryQuery = "SELECT c.id, c.name FROM category c " +
                "JOIN l_films_category fc ON fc.category_id = c.id " +
                "JOIN film f ON f.id = fc.films_id " +
                "WHERE f.id = ?";
        List<Category> categories = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(categoryQuery)
        ) {
            preparedStatement.setLong(1, filmId);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    Category category = new Category();
                    category.setId(rs.getLong("id"));
                    category.setName(rs.getString("name"));
                    categories.add(category);
                }
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException("SQL Issues!");
        }
        return categories;
    }
}