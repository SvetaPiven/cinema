package org.example.service.impl;

import org.example.entity.Language;
import repository.LanguageRepository;
import org.example.service.LanguageService;

import java.util.List;

public class LanguageServiceImpl implements LanguageService {

    private final LanguageRepository languageRepository;

    public LanguageServiceImpl(LanguageRepository languageRepository) {
        this.languageRepository = languageRepository;
    }

    @Override
    public List<Language> findAll() {
        return languageRepository.findAll();
    }

    @Override
    public Language findById(Long id) {
        return languageRepository.findById(id);
    }

    @Override
    public boolean delete(Long id) {
        return languageRepository.delete(id);
    }
}
