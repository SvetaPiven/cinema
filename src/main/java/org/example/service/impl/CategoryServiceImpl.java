package org.example.service.impl;

import org.example.entity.Category;
import org.example.repository.CategoryRepository;
import org.example.service.CategoryService;

import java.util.List;

public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Category findById(Long id) {
        return categoryRepository.findById(id);
    }

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public boolean delete(Long id) {
        return categoryRepository.delete(id);
    }
}