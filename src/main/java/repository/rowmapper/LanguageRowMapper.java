package repository.rowmapper;

import org.example.entity.Category;
import org.example.entity.Film;
import org.example.entity.Language;
import repository.impl.FilmRepositoryImpl;
import repository.impl.LanguageRepositoryImpl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class LanguageRowMapper {

    private final FilmRepositoryImpl filmRepository;

    private final Connection connection;

    private final LanguageRepositoryImpl languageRepository;

    public LanguageRowMapper(FilmRepositoryImpl filmRepository, Connection connection, LanguageRepositoryImpl languageRepository) {
        this.filmRepository = filmRepository;
        this.connection = connection;
        this.languageRepository = languageRepository;
    }

    public Language processResultSetLanguage(ResultSet rs) {
        Language language;

        try {
            language = new Language();
            language.setId(rs.getLong(LanguageRepositoryImpl.ID));
            language.setName(rs.getString(LanguageRepositoryImpl.NAME));
            List<Film> films = languageRepository.getFilmsForLanguage(language.getId(), connection);

            for (Film film : films) {
                List<Category> categories = filmRepository.getCategoriesForFilm(film.getId(), connection);
                film.setCategories(categories);
            }
            language.setFilms(films);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return language;
    }
}