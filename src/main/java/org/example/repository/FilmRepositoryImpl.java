package org.example.repository;

import org.example.entity.Film;

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

    private Film parseResultSet(ResultSet rs) {

        Film film;

        try {
            film = new Film();
            film.setId(rs.getLong(ID));
            film.setTitle(rs.getString(TITLE));
            film.setLanguageId(rs.getLong(LANGUAGE_ID));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return film;
    }

    @Override
    public Film findOne(Long id) {
        final String findOneQuery = "select * from film where id = " + id;

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
    public boolean delete(Long id) {
        final String deleteQuery = "delete from film where id = " + id;

        registerDriver();
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()
        ) {
            statement.executeUpdate(deleteQuery);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException("SQL Issues!");
        }
        return false;
    }

    @Override
    public Film update(Film film) {
        final String updateQuery = "update film set title = ?, language_id = ? where id = ?";

        registerDriver();
        try (Connection connection = getConnection();
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

        registerDriver();
        try (Connection connection = getConnection();
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
