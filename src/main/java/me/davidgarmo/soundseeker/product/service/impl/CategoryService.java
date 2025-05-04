package me.davidgarmo.soundseeker.product.service.impl;

import me.davidgarmo.soundseeker.product.persistence.entity.CategoryEntity;
import me.davidgarmo.soundseeker.product.persistence.mapper.CategoryMapper;
import me.davidgarmo.soundseeker.product.persistence.repository.CategoryRepository;
import me.davidgarmo.soundseeker.product.service.ICategoryService;
import me.davidgarmo.soundseeker.product.service.dto.CategoryDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService implements ICategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public CategoryService(CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    @Override
    public CategoryDto save(CategoryDto categoryDto) {
        if (this.categoryRepository.existsByNameIgnoreCase(categoryDto.name())) {
            throw new IllegalArgumentException("Category name already exists.");
        }
        CategoryEntity category = this.categoryMapper.toEntity(categoryDto);
        CategoryEntity savedCategory = this.categoryRepository.save(category);
        return this.categoryMapper.toDto(savedCategory);
    }

    @Override
    public CategoryDto findById(Long id) {
        return null;
    }

    @Override
    public List<CategoryDto> findAll() {
        return List.of();
    }

    @Override
    public CategoryDto update(CategoryDto categoryDto) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }
}
