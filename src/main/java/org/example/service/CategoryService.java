package org.example.service;

import org.example.entity.dto.CategoryDto;

import java.util.List;

public interface CategoryService {

    CategoryDto findById(Long id);

    List<CategoryDto> findAll();

    CategoryDto create(CategoryDto categoryDto);

    CategoryDto update(CategoryDto categoryDto, Long id);

    void delete(Long id);

}
