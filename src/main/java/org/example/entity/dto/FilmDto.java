package org.example.entity.dto;


import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;

public class FilmDto implements Serializable {

    @Size(min = 2, max = 256)
    @NotNull(message = "Title must not be null")
    private String title;

    public FilmDto(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    @Override
    public String toString() {
        return "Film{" +
                "title='" + title + '\'' +
                '}';
    }
}
