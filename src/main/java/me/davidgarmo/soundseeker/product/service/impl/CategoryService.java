package me.davidgarmo.soundseeker.product.service.impl;

import me.davidgarmo.soundseeker.product.persistence.entity.CategoryEntity;
import me.davidgarmo.soundseeker.product.persistence.mapper.CategoryMapper;
import me.davidgarmo.soundseeker.product.persistence.repository.CategoryRepository;
import me.davidgarmo.soundseeker.product.service.ICategoryService;
import me.davidgarmo.soundseeker.product.service.dto.CategoryDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
        return this.categoryRepository.findById(id)
                .map(categoryMapper::toDto)
                .orElseThrow(() -> new IllegalArgumentException("Category not found."));
    }

    @Override
    public List<CategoryDto> findAll() {
        return this.categoryRepository.findAll()
                .stream()
                .map(categoryMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryDto update(CategoryDto categoryDto) {
        if (categoryDto.id() == null) {
            throw new IllegalArgumentException("Category ID cannot be null.");
        }
        if (this.categoryRepository.existsByNameIgnoreCaseAndIdNot(categoryDto.name(), categoryDto.id())) {
            throw new IllegalArgumentException("Category name already exists.");
        }
        CategoryEntity categoryToUpdate = this.categoryMapper.toEntity(categoryDto);
        CategoryEntity updatedCategory = this.categoryRepository.save(categoryToUpdate);
        return this.categoryMapper.toDto(updatedCategory);
    }

    @Override
    public void deleteById(Long id) {
        if (!this.categoryRepository.existsById(id)) {
            throw new IllegalArgumentException("Category not found.");
        }
        this.categoryRepository.deleteById(id);
    }
}
