package org.example.service.impl;

import org.example.entity.Film;
import org.example.entity.dto.FilmDto;
import org.example.mapper.FilmRowMapper;
import org.example.repository.FilmRepository;
import org.example.service.FilmService;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class FilmServiceImpl implements FilmService {

    private final FilmRepository filmRepository;

    private final FilmRowMapper filmRowMapper;

    public FilmServiceImpl(FilmRepository filmRepository, FilmRowMapper filmRowMapper) {
        this.filmRepository = filmRepository;
        this.filmRowMapper = filmRowMapper;
    }

    @Override
    public FilmDto findById(Long id) {
        Film film = filmRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Film not found"));
        return filmRowMapper.toDto(film);
    }

    @Override
    public List<FilmDto> findAll() {
        List<Film> films = filmRepository.findAll();
        return films.stream()
                .map(filmRowMapper::toDto)
                .toList();
    }

    @Override
    public FilmDto create(FilmDto filmDto) {
        Film film = filmRowMapper.toEntity(filmDto);
        setDefaultLanguage(film);
        return filmRowMapper.toDto(filmRepository.saveAndFlush(film));
    }

    @Override
    public FilmDto update(FilmDto filmDto, Long id) {
        Film film = filmRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Film with id " + id + " not found"));

        filmRowMapper.partialUpdate(filmDto, film);

        return filmRowMapper.toDto(filmRepository.saveAndFlush(film));
    }

    @Override
    public void delete(Long id) {
        filmRepository.deleteById(id);
    }

    private void setDefaultLanguage(Film film) {
        if (film.getLanguageId() == null) {
            film.setLanguageId(1L);
        }
    }
}
