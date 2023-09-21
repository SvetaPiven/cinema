package org.example.entity.dto;


import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

public class FilmDto implements Serializable {

    @Size(min = 2, max = 256)
    @NotNull(message = "Title must not be null")
    private String title;

    @NotNull(message = "Id must not be null")
    private Long languageId;

    public FilmDto(String title, Long languageId) {
        this.title = title;
        this.languageId = languageId;
    }

    public FilmDto() {
    }

    public FilmDto(String title) {
        this.title = title;
    }

    public Long getLanguageId() {
        return languageId;
    }

    public void setLanguageId(Long languageId) {
        this.languageId = languageId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "FilmDto{" +
                "title='" + title + '\'' +
                ", languageId=" + languageId +
                '}';
    }
}
