package org.example.service.impl;

import org.example.entity.Category;
import org.example.entity.dto.CategoryDto;
import org.example.mapper.CategoryRowMapper;
import org.example.repository.CategoryRepository;
import org.example.service.CategoryService;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    private final CategoryRowMapper categoryRowMapper;

    public CategoryServiceImpl(CategoryRepository categoryRepository, CategoryRowMapper categoryRowMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryRowMapper = categoryRowMapper;
    }

    @Override
    public CategoryDto findById(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Category not found"));
        return categoryRowMapper.toDto(category);
    }

    @Override
    public List<CategoryDto> findAll() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream()
                .map(categoryRowMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryDto create(CategoryDto categoryDto) {
        Category category = categoryRowMapper.toEntity(categoryDto);

        categoryRepository.saveAndFlush(category);
        return categoryRowMapper.toDto(categoryRepository.saveAndFlush(category));
    }


    @Override
    public CategoryDto update(CategoryDto categoryDto, Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Category with id " + id + " not found"));

        categoryRowMapper.partialUpdate(categoryDto, category);

        return categoryRowMapper.toDto(categoryRepository.saveAndFlush(category));
    }

    @Override
    public void delete(Long id) {
        categoryRepository.deleteById(id);
    }
}