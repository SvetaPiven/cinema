package org.example.service;

import org.example.entity.Category;

import java.util.List;

public interface CategoryService {
    Category findById(Long id);
    List<Category> findAll();
    boolean delete(Long id);
}
