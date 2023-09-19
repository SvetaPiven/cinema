package org.example.entity.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

public class LanguageDto implements Serializable {

    @Size(min = 2, max = 256)
    @NotNull(message = "Name must not be null")
    private String name;
    public LanguageDto() {
    }
    public LanguageDto(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Language{" +
                "name='" + name + '\'' +
                '}';
    }
}
