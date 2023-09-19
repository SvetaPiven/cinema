//package org.example.service;
//
//import org.example.entity.Category;
//import org.example.entity.Film;
//import org.example.repository.FilmRepository;
//import org.example.service.impl.FilmServiceImpl;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.Collections;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//import static org.mockito.Mockito.when;
//
//@ExtendWith(MockitoExtension.class)
//class FilmServiceTest {
//
//    @Mock
//    private FilmRepository filmRepository;
//
//    private FilmService filmService;
//
//    @BeforeEach
//    public void setUp() {
//        filmService = new FilmServiceImpl(filmRepository);
//    }
//
//    @Test
//    void testFindById() {
//        Long filmId = 2L;
//        Film test = new Film();
//        test.setId(filmId);
//        test.setTitle("New Film");
//
//        when(filmRepository.findById(filmId)).thenReturn(test);
//
//        Film film = filmService.findById(filmId);
//
//        assertEquals(filmId, film.getId());
//        assertEquals("New Film", film.getTitle());
//    }
//
//    @Test
//    void testFindAll() {
//        Category category1 = new Category(1L, "1 Category");
//        Category category2 = new Category(2L, "2 Category");
//
//        List<Film> testFilms = new ArrayList<>();
//        testFilms.add(new Film(1L, "1 Film", 1L, Arrays.asList(category1, category2)));
//        testFilms.add(new Film(2L, "2 Film", 2L, Collections.singletonList(category2)));
//        testFilms.add(new Film(3L, "3 Film", 3L, Collections.singletonList(category1)));
//
//        when(filmRepository.findAll()).thenReturn(testFilms);
//
//        List<Film> films = filmService.findAll();
//
//        assertEquals(testFilms.size(), films.size());
//    }
//
//    @Test
//    void testUpdate() {
//        Long id = 3L;
//        Film testFilm = new Film();
//        testFilm.setId(id);
//        testFilm.setTitle("New Film");
//
//        when(filmRepository.update(testFilm)).thenReturn(testFilm);
//
//        Film updatedFilm = filmService.update(testFilm);
//
//        assertEquals(id, updatedFilm.getId());
//        assertEquals("New Film", updatedFilm.getTitle());
//    }
//
//    @Test
//    void testCreate() {
//        Film testFilm = new Film();
//        testFilm.setTitle("New Film");
//
//        when(filmRepository.create(testFilm)).thenReturn(testFilm);
//
//        Film createdFilm = filmService.create(testFilm);
//
//        assertEquals("New Film", createdFilm.getTitle());
//    }
//
//    @Test
//    void testDelete() {
//        Long filmIdToDelete = 1L;
//
//        when(filmRepository.delete(filmIdToDelete)).thenReturn(true);
//
//        boolean result = filmService.delete(filmIdToDelete);
//
//        assertTrue(result);
//    }
//}
//
