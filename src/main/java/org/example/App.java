package org.example;

import org.example.entity.Film;
import org.example.repository.FilmRepository;
import org.example.repository.FilmRepositoryImpl;

import java.util.List;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        FilmRepository filmRepository = new FilmRepositoryImpl();

        List<Film> all = filmRepository.findAll();

        for (Film film : all) {
            System.out.println(film);
        }
    }
}
