package org.example.entity;

import lombok.Data;

import java.util.List;

@Data
public class Film {

    private Long id;

    private String title;

    private Long languageId;

    private List<Category> categories;

}
