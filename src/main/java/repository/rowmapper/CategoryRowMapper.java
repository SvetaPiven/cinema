package repository.rowmapper;

import org.example.entity.Category;
import org.example.entity.Film;
import repository.impl.CategoryRepositoryImpl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class CategoryRowMapper {

    private final Connection connection;

    private final CategoryRepositoryImpl categoryRepository;

    public CategoryRowMapper(Connection connection, CategoryRepositoryImpl categoryRepository) {
        this.connection = connection;
        this.categoryRepository = categoryRepository;
    }

    public Category processResultSetCategory(ResultSet rs) {
        try {
            Category category = new Category();
            category.setId(rs.getLong(CategoryRepositoryImpl.ID));
            category.setName(rs.getString(CategoryRepositoryImpl.NAME));
            List<Film> films = categoryRepository.getFilmsForCategory(category.getId(), connection);
            category.setFilms(films);
            return category;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
