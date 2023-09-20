package org.example.service;

import org.example.entity.Language;
import org.example.entity.dto.LanguageDto;
import org.example.mapper.LanguageRowMapper;
import org.example.repository.LanguageRepository;
import org.example.service.impl.LanguageServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityNotFoundException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LanguageServiceImplTest {

    @Mock
    private LanguageRepository languageRepository;

    @Mock
    private LanguageRowMapper languageRowMapper;

    private LanguageService languageService;

    @BeforeEach
    void setUp() {
        languageService = new LanguageServiceImpl(languageRepository, languageRowMapper);
    }

    @Test
    void testFindById() {
        Long languageId = 1L;
        Language language = new Language();
        language.setId(languageId);
        language.setName("Test Language");

        when(languageRepository.findById(languageId)).thenReturn(Optional.of(language));
        when(languageRowMapper.toDto(language)).thenReturn(new LanguageDto(language.getName()));

        LanguageDto result = languageService.findById(languageId);

        assertNotNull(result);
        assertEquals(language.getName(), result.getName());
    }

    @Test
    void testFindByIdNotFound() {
        Long languageId = 1L;

        when(languageRepository.findById(languageId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> languageService.findById(languageId));
    }

    @Test
    void testFindAll() {
        List<Language> languages = Collections.singletonList(new Language(1L, "Test Language"));

        when(languageRepository.findAll()).thenReturn(languages);
        when(languageRowMapper.toDto(any(Language.class))).thenReturn(new LanguageDto("Test Language"));

        List<LanguageDto> result = languageService.findAll();

        assertEquals(1, result.size());
        assertEquals("Test Language", result.get(0).getName());
    }

    @Test
    void testCreateLanguage() {
        LanguageDto languageDto = new LanguageDto("New Language");
        Language language = new Language();
        language.setName(languageDto.getName());

        when(languageRowMapper.toEntity(languageDto)).thenReturn(language);
        when(languageRowMapper.toDto(language)).thenReturn(languageDto);
        when(languageRepository.saveAndFlush(language)).thenReturn(language);

        LanguageDto result = languageService.create(languageDto);

        assertNotNull(result);
        assertEquals(languageDto.getName(), result.getName());
    }

    @Test
    void testUpdateLanguage() {
        Long id = 1L;
        LanguageDto languageDto = new LanguageDto("Updated Language");

        Language language = new Language();
        language.setId(id);
        language.setName("Language");

        Language updatedLanguage = new Language();
        updatedLanguage.setId(id);
        updatedLanguage.setName(languageDto.getName());

        when(languageRepository.findById(id)).thenReturn(Optional.of(language));
        when(languageRowMapper.toDto(updatedLanguage)).thenReturn(languageDto);
        when(languageRepository.saveAndFlush(any(Language.class))).thenReturn(updatedLanguage);

        LanguageService languageService = new LanguageServiceImpl(languageRepository, languageRowMapper);
        LanguageDto result = languageService.update(languageDto, id);

        assertNotNull(result);
        assertEquals(languageDto.getName(), result.getName());
    }

    @Test
    void testDelete() {
        Long id = 17L;

        languageService.delete(id);

        verify(languageRepository, times(1)).deleteById(id);
    }
}