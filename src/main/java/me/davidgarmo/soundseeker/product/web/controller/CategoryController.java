package me.davidgarmo.soundseeker.product.web.controller;

import jakarta.validation.Valid;
import me.davidgarmo.soundseeker.product.service.ICategoryService;
import me.davidgarmo.soundseeker.product.service.dto.CategoryDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {
    private final ICategoryService categoryService;

    public CategoryController(ICategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    public ResponseEntity<CategoryDto> save(@Valid @RequestBody CategoryDto categoryDto) {
        return ResponseEntity.status(201).body(this.categoryService.save(categoryDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(this.categoryService.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<CategoryDto>> findAll() {
        return ResponseEntity.ok(this.categoryService.findAll());
    }

    @PutMapping
    public ResponseEntity<CategoryDto> update(@Valid @RequestBody CategoryDto categoryDto) {
        return ResponseEntity.ok(this.categoryService.update(categoryDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        this.categoryService.deleteById(id);
        return ResponseEntity.status(204).build();
    }
}
