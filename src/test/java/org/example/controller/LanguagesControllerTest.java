package org.example.controller;

import org.example.entity.dto.LanguageDto;
import org.example.service.LanguageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class LanguagesControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private LanguagesController languagesController;

    @Mock
    private LanguageService languageService;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(languagesController).build();
    }

    @Test
    void testFindAllLanguages() throws Exception {
        List<LanguageDto> languages = Arrays.asList(
                new LanguageDto("Language 1"),
                new LanguageDto("Language 2")
        );

        when(languageService.findAll()).thenReturn(languages);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/languages")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].name").value("Language 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[1].name").value("Language 2"));
    }

    @Test
    void testFindLanguageById() throws Exception {
        Long languageId = 1L;
        LanguageDto languageDto = new LanguageDto("Test Language");

        when(languageService.findById(languageId)).thenReturn(languageDto);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/languages/{id}", languageId)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Test Language"));

    }

    @Test
    void testCreateLanguage() throws Exception {
        LanguageDto languageDto = new LanguageDto("New Language");

        when(languageService.create(any(LanguageDto.class))).thenReturn(languageDto);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/languages")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(languageDto))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").exists());

    }

    @Test
    void testUpdateLanguage() throws Exception {
        Long languageId = 1L;
        LanguageDto languageDto = new LanguageDto("Updated Language");

        when(languageService.update(Mockito.any(LanguageDto.class), Mockito.eq(languageId)))
                .thenReturn(languageDto);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/languages/{id}", languageId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(languageDto))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Updated Language"));
    }

    @Test
    void testDeleteLanguageById() throws Exception {
        Long languageId = 1L;

        mockMvc.perform(MockMvcRequestBuilders.delete("/languages/{id}", languageId))
                .andExpect(status().isOk());
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
