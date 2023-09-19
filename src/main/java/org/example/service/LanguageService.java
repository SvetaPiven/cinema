package org.example.service;

import org.example.entity.dto.LanguageDto;

import java.util.List;

public interface LanguageService {
    LanguageDto findById(Long id);

    List<LanguageDto> findAll();

    LanguageDto create(LanguageDto languageDto);

    LanguageDto update(LanguageDto languageDto, Long id);

    void delete(Long id);
}
