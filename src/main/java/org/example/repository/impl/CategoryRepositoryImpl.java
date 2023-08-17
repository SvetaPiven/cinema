package org.example.repository.impl;

import org.example.entity.Category;
import org.example.repository.BaseRepository;
import org.example.repository.CategoryRepository;
import org.example.repository.rowmapper.CategoryRowMapper;

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
        final String query = "DELETE FROM category WHERE id = ?";
        final String linkQuery = "DELETE FROM l_films_category WHERE category_id = ?";

        registerDriver();
        try (Connection connection = getConnection();
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
        CategoryRowMapper rowMapper = new CategoryRowMapper(getConnection());
        return rowMapper.processResultSetCategory(rs);
    }
}