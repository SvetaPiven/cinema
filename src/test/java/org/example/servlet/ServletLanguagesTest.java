package org.example.servlet;

import org.example.entity.Language;
import org.example.service.impl.LanguageServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ServletLanguagesTest {

    @Mock
    private LanguageServiceImpl languageService;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @InjectMocks
    private ServletLanguages servlet;

    private PrintWriter writer;

    @BeforeEach
    void setUp() {
        servlet.init();
        StringWriter stringWriter = new StringWriter();
        writer = new PrintWriter(stringWriter);
    }

    @Test
    void testDeleteValidLanguageId() throws Exception {
        Long languageIdToDelete = 1L;
        when(request.getParameter("id")).thenReturn("2");
        when(response.getWriter()).thenReturn(writer);
        when(languageService.delete(languageIdToDelete)).thenReturn(true);

        servlet.doDelete(request, response);
        boolean result = languageService.delete(languageIdToDelete);

        assertTrue(result);
    }

    @Test
    void testFindByIdValidLanguageId() throws Exception {
        Long languageId = 1L;
        Language test = new Language();
        test.setId(languageId);
        test.setName("New Language");

        when(request.getParameter("id")).thenReturn(null);
        when(languageService.findById(languageId)).thenReturn(test);
        when(response.getWriter()).thenReturn(writer);

        servlet.doGet(request, response);

        Language language = languageService.findById(languageId);

        assertEquals(languageId, language.getId());
        assertEquals("New Language", language.getName());
    }

    @Test
    void testValidFindAll() throws Exception {
        List<Language> languages = new ArrayList<>();
        languages.add(new Language(1L, "Language 1"));
        languages.add(new Language(2L, "Language 2"));
        when(languageService.findAll()).thenReturn(languages);
        when(response.getWriter()).thenReturn(writer);

        servlet.doGet(request, response);
        List<Language> test = languageService.findAll();

        assertEquals(languages.size(), test.size());
    }

    @Test
    void testInvalidLanguageId() throws Exception {
        when(request.getParameter("id")).thenReturn("invalid_id");

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        servlet.doGet(request, response);

        assertTrue(stringWriter.toString().contains("Invalid language ID"));
    }
}
