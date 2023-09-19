//package org.example.service;
//
//import org.example.entity.Film;
//import org.example.entity.Language;
//import org.example.repository.LanguageRepository;
//import org.example.service.impl.LanguageServiceImpl;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//import static org.mockito.Mockito.when;
//
//@ExtendWith(MockitoExtension.class)
//class LanguageServiceTest {
//
//    @Mock
//    private LanguageRepository languageRepository;
//
//    private LanguageService languageService;
//
//    @BeforeEach
//    public void setUp() {
//        languageService = new LanguageServiceImpl(languageRepository);
//    }
//
//    @Test
//    void testFindById() {
//        Long languageId = 1L;
//        Language testLanguage = new Language();
//        testLanguage.setId(languageId);
//        testLanguage.setName("Test Language");
//
//        when(languageRepository.findById(languageId)).thenReturn(testLanguage);
//
//        Language language = languageService.findById(languageId);
//
//        assertEquals(languageId, language.getId());
//        assertEquals("Test Language", language.getName());
//    }
//
//    @Test
//    void testFindAll() {
//        Film film = new Film(1L, "New Film");
//
//        List<Language> test = new ArrayList<>();
//        test.add(new Language(1L, "Language 1", List.of(film)));
//        test.add(new Language(2L, "Language 2", List.of(film)));
//        test.add(new Language(3L, "Language 3", List.of(film)));
//
//        when(languageRepository.findAll()).thenReturn(test);
//
//        List<Language> languages = languageService.findAll();
//
//        assertEquals(test.size(), languages.size());
//    }
//
//    @Test
//    void testDelete() {
//        Long languageIdToDelete = 1L;
//
//        when(languageRepository.delete(languageIdToDelete)).thenReturn(true);
//
//        boolean result = languageService.delete(languageIdToDelete);
//
//        assertTrue(result);
//    }
//}
