package org.example.service;

import org.example.entity.dto.FilmDto;

import java.util.List;

public interface FilmService {
    FilmDto findById(Long id);

    List<FilmDto> findAll();

    FilmDto create(FilmDto filmDto);

    FilmDto update(FilmDto filmDto, Long id);

    void delete(Long id);
}
