package org.example.controller;

import org.example.entity.dto.FilmDto;
import org.example.service.FilmService;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class FilmsControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private FilmsController filmsController;

    @Mock
    private FilmService filmService;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(filmsController).build();
    }

    @Test
    void testFindAllFilms() throws Exception {
        List<FilmDto> films = Arrays.asList(
                new FilmDto("Film 1", 1L),
                new FilmDto("Film 2", 2L)
        );

        when(filmService.findAll()).thenReturn(films);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/films")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].title").value("Film 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[1].title").value("Film 2"));
    }

    @Test
    void testFindFilmById() throws Exception {
        Long filmId = 1L;
        FilmDto filmDto = new FilmDto("Test Film", 2L);

        when(filmService.findById(filmId)).thenReturn(filmDto);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/films/{id}", filmId)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Test Film"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.languageId").value(2L));
    }

    @Test
    void testCreateFilm() throws Exception {
        FilmDto filmDto = new FilmDto("New Film", 1L);

        when(filmService.create(any(FilmDto.class))).thenReturn(filmDto);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/films")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(filmDto))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.languageId").exists());
    }

    @Test
    void testUpdateFilm() throws Exception {
        Long filmId = 1L;
        FilmDto filmDto = new FilmDto("Updated Film", 1L);

        when(filmService.update(Mockito.any(FilmDto.class), Mockito.eq(filmId)))
                .thenReturn(filmDto);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/films/{id}", filmId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(filmDto))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Updated Film"));
    }

    @Test
    void testDeleteFilmById() throws Exception {
        Long filmId = 1L;

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/films/{id}", filmId))
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


