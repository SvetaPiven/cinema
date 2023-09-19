package org.example.service.impl;

import org.example.entity.dto.LanguageDto;
import org.example.entity.Language;
import org.example.mapper.LanguageRowMapper;
import org.example.repository.LanguageRepository;
import org.example.service.LanguageService;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LanguageServiceImpl implements LanguageService {

    private final LanguageRepository languageRepository;

    private final LanguageRowMapper languageRowMapper;

    public LanguageServiceImpl(LanguageRepository languageRepository, LanguageRowMapper languageRowMapper) {
        this.languageRepository = languageRepository;
        this.languageRowMapper = languageRowMapper;
    }

    @Override
    public LanguageDto findById(Long id) {
        Language language = languageRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Language not found"));
        return languageRowMapper.toDto(language);
    }

    @Override
    public List<LanguageDto> findAll() {
        List<Language> languages = languageRepository.findAll();
        return languages.stream()
                .map(languageRowMapper::toDto)
                .toList();
    }

    @Override
    public LanguageDto create(LanguageDto languageDto) {
        Language language = languageRowMapper.toEntity(languageDto);

        languageRepository.saveAndFlush(language);
        return languageRowMapper.toDto(languageRepository.saveAndFlush(language));
    }

    @Override
    public LanguageDto update(LanguageDto languageDto, Long id) {
        Language language = languageRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Language with id " + id + " not found"));

        languageRowMapper.partialUpdate(languageDto, language);

        return languageRowMapper.toDto(languageRepository.saveAndFlush(language));
    }

    @Override
    public void delete(Long id) {
        languageRepository.deleteById(id);
    }
}
