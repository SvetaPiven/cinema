package org.example.entity;

import java.util.List;
import java.util.Objects;

public class Film {

    private Long id;

    private String title;

    private Long languageId;

    private List<Category> categories;

    public Film() {
    }

    public Film(Long id, String title, Long languageId) {
        this.id = id;
        this.title = title;
        this.languageId = languageId;
    }

    public Film(Long id, String title) {
        this.id = id;
        this.title = title;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getLanguageId() {
        return languageId;
    }

    public void setLanguageId(Long languageId) {
        this.languageId = languageId;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public Film(Long id, String title, Long languageId, List<Category> categories) {
        this.id = id;
        this.title = title;
        this.languageId = languageId;
        this.categories = categories;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Film film = (Film) o;
        return Objects.equals(id, film.id) && Objects.equals(title, film.title) && Objects.equals(languageId, film.languageId) && Objects.equals(categories, film.categories);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, languageId, categories);
    }

    @Override
    public String toString() {
        return "Film{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", languageId=" + languageId +
                ", categories=" + categories +
                '}';
    }
}
