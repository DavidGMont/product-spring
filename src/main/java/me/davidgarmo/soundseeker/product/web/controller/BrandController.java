package me.davidgarmo.soundseeker.product.web.controller;

import jakarta.validation.Valid;
import me.davidgarmo.soundseeker.product.service.IBrandService;
import me.davidgarmo.soundseeker.product.service.dto.BrandDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/brands")
@CrossOrigin
public class BrandController {
    private final IBrandService brandService;

    public BrandController(IBrandService brandService) {
        this.brandService = brandService;
    }

    @PostMapping
    public ResponseEntity<BrandDto> save(@Valid @RequestBody BrandDto brandDto) {
        return ResponseEntity.status(201).body(this.brandService.save(brandDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BrandDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(this.brandService.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<BrandDto>> findAll() {
        return ResponseEntity.ok(this.brandService.findAll());
    }

    @PutMapping
    public ResponseEntity<BrandDto> update(@Valid @RequestBody BrandDto brandDto) {
        return ResponseEntity.ok(this.brandService.update(brandDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        this.brandService.deleteById(id);
        return ResponseEntity.status(204).build();
    }
}
