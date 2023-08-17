package org.example.service;

import org.example.entity.Language;

import java.util.List;

public interface LanguageService {
    List<Language> findAll();

    Language findById(Long id);

    boolean delete(Long id);
}
