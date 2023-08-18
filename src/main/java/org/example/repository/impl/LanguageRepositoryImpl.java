package org.example.repository.impl;

import org.example.entity.Language;
import org.example.repository.BaseRepository;
import org.example.repository.LanguageRepository;
import org.example.repository.rowmapper.FilmRowMapper;
import org.example.repository.rowmapper.LanguageRowMapper;
import org.example.util.BaseConnection;

import java.sql.Connection;
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

    private Language parseResultSet(ResultSet rs) {
        LanguageRowMapper rowMapper = new LanguageRowMapper(new FilmRowMapper(BaseConnection.getConnection()), BaseConnection.getConnection());
        return rowMapper.processResultSetLanguage(rs);
    }

    @Override
    public Language findById(Long id) {
        final String findOneQuery = "SELECT * FROM language WHERE id = " + id;

        BaseConnection.registerDriver();
        try (Connection connection = BaseConnection.getConnection();
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

        BaseConnection.registerDriver();
        try (Connection connection = BaseConnection.getConnection();
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