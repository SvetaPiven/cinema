package org.example.service;

import org.example.entity.Film;

import java.util.List;

public interface FilmService {
    List<Film> findAll();

    Film findById(Long id);

    boolean delete(Long id);

    Film update(Film film);

    Film create(Film film);
}
