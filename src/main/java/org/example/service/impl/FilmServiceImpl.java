package org.example.service.impl;

import org.example.entity.Film;
import repository.FilmRepository;
import org.example.service.FilmService;

import java.util.List;

public class FilmServiceImpl implements FilmService {

    private final FilmRepository filmRepository;

    public FilmServiceImpl(FilmRepository filmRepository) {
        this.filmRepository = filmRepository;
    }

    @Override
    public List<Film> findAll() {
        return filmRepository.findAll();
    }

    @Override
    public Film findById(Long id) {
        return filmRepository.findById(id);
    }

    @Override
    public boolean delete(Long id) {
        return filmRepository.delete(id);
    }

    @Override
    public Film update(Film film) {
        return filmRepository.update(film);
    }

    @Override
    public Film create(Film film) {
        return filmRepository.create(film);
    }
}
