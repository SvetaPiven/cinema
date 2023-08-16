package org.example.repository;

import org.example.entity.Language;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class LanguageRepositoryImpl extends BaseRepository implements LanguageRepository {

    public static final String ID = "id";
    public static final String NAME = "name";

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

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return language;
    }

    @Override
    public Language findOne(Long id) {
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
        return false;
    }

    @Override
    public Language update(Language language) {
        // Implement the update logic here
        return null;
    }

    @Override
    public Language create(Language language) {
        // Implement the create logic here
        return null;
    }
}