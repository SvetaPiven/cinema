package org.example.repository.impl;

import org.example.entity.Film;
import org.example.repository.BaseRepository;
import org.example.repository.FilmRepository;
import org.example.repository.rowmapper.FilmRowMapper;
import org.example.util.BaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class FilmRepositoryImpl extends BaseRepository implements FilmRepository {

    public static final String ID = "id";

    public static final String TITLE = "title";

    public static final String LANGUAGE_ID = "language_id";

    @Override
    public List<Film> findAll() {
        final String findAllQuery = "select * from film order by id ";

        List<Film> result = new ArrayList<>();

        BaseConnection.registerDriver();
        try (Connection connection = BaseConnection.getConnection();
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
        FilmRowMapper rowMapper = new FilmRowMapper(BaseConnection.getConnection());
        return rowMapper.processResultSetFilm(rs);
    }

    @Override
    public Film findById(Long id) {
        final String findByIdQuery = "select * from film where id = " + id;

        BaseConnection.registerDriver();
        try (Connection connection = BaseConnection.getConnection();
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

        BaseConnection.registerDriver();
        try (Connection connection = BaseConnection.getConnection();
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

        BaseConnection.registerDriver();
        try (Connection connection = BaseConnection.getConnection();
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

        BaseConnection.registerDriver();
        try (Connection connection = BaseConnection.getConnection();
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
}