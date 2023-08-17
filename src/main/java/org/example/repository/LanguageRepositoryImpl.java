package org.example.repository;

import org.example.entity.Category;
import org.example.entity.Film;
import org.example.entity.Language;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class LanguageRepositoryImpl extends BaseRepository implements LanguageRepository {

    public static final String ID = "id";

    public static final String NAME = "name";

    private FilmRepositoryImpl filmRepositoryImpl;

    public LanguageRepositoryImpl() {
        filmRepositoryImpl = new FilmRepositoryImpl();
    }

    @Override
    public List<Language> findAll() {
        final String findAllQuery = "SELECT * FROM language ORDER BY id";

        List<Language> result = new ArrayList<>();

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

    private Language parseResultSet(ResultSet rs) {
        Language language;

        try {
            language = new Language();
            language.setId(rs.getLong(ID));
            language.setName(rs.getString(NAME));
            List<Film> films = getFilmsForLanguages(language.getId());

            for (Film film : films) {
                List<Category> categories = filmRepositoryImpl.getCategoriesForFilm(film.getId());
                film.setCategories(categories);
            }
            language.setFilms(films);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return language;
    }

    private List<Film> getFilmsForLanguages(Long languageId) {
        final String languageQuery = "SELECT f.id, f.title FROM language l " +
                "JOIN film f ON f.language_id = l.id " +
                "WHERE l.id = ?";
        List<Film> films = new ArrayList<>();

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(languageQuery)
        ) {
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
            System.err.println(e.getMessage());
            throw new RuntimeException("SQL Issues!");
        }
        return films;
    }


    @Override
    public Language findById(Long id) {
        final String findOneQuery = "SELECT * FROM language WHERE id = " + id;

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
        final String deleteQuery = "DELETE FROM language WHERE id = " + id;

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

    @Override
    public Language update(Language language) {
        return null;
    }

    @Override
    public Language create(Language language) {
        return null;
    }
}