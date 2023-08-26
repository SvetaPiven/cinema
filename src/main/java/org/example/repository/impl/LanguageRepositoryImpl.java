package org.example.repository.impl;

import org.example.entity.Film;
import org.example.entity.Language;
import org.example.repository.LanguageRepository;
import org.example.repository.rowmapper.LanguageRowMapper;
import org.example.util.BaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class LanguageRepositoryImpl implements LanguageRepository {

    public static final String ID = "id";

    public static final String NAME = "name";

    private final BaseConnection baseConnection;

    private final FilmRepositoryImpl filmRepositoryImpl = new FilmRepositoryImpl(null);

    public LanguageRepositoryImpl(BaseConnection baseConnection) {
        this.baseConnection = baseConnection;
    }

    @Override
    public List<Language> findAll() {
        final String findAllQuery = "SELECT * FROM language ORDER BY id";

        List<Language> result = new ArrayList<>();

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

    private Language parseResultSet(ResultSet rs) {
        LanguageRowMapper rowMapper = new LanguageRowMapper(filmRepositoryImpl, baseConnection.getConnection(), this);
        return rowMapper.processResultSetLanguage(rs);
    }

    @Override
    public Language findById(Long id) {
        final String findOneQuery = "SELECT * FROM language WHERE id = " + id;

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
    public boolean delete(Long id) {
        final String deleteFilmQuery = "DELETE FROM film WHERE language_id = ?";
        final String deleteFilmCategoryLinksQuery = "DELETE FROM l_films_category WHERE films_id = ?";
        final String deleteLanguageQuery = "DELETE FROM language WHERE id = ?";

        baseConnection.registerDriver();
        try (Connection connection = baseConnection.getConnection();
             PreparedStatement deleteFilmStatement = connection.prepareStatement(deleteFilmQuery);
             PreparedStatement deleteFilmCategoryLinksStatement = connection.prepareStatement(deleteFilmCategoryLinksQuery);
             PreparedStatement deleteLanguageStatement = connection.prepareStatement(deleteLanguageQuery)
        ) {
            deleteFilmStatement.setLong(1, id);
            deleteFilmStatement.executeUpdate();

            deleteFilmCategoryLinksStatement.setLong(1, id);
            deleteFilmCategoryLinksStatement.executeUpdate();

            deleteLanguageStatement.setLong(1, id);
            deleteLanguageStatement.executeUpdate();

            return true;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException("SQL Issues!");
        }
    }

    @Override
    public Language update(Language language) {
        return null;
    }

    @Override
    public Language create(Language language) {
        return null;
    }

    public List<Film> getFilmsForLanguage(Long languageId, Connection connection) {
        final String languageQuery = "SELECT f.id, f.title FROM language l " +
                "JOIN film f ON f.language_id = l.id " +
                "WHERE l.id = ?";
        List<Film> films = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(languageQuery)) {
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
            throw new RuntimeException("SQL Issues!", e);
        }
        return films;
    }
}
