package me.davidgarmo.soundseeker.product.web.controller;

import jakarta.validation.Valid;
import me.davidgarmo.soundseeker.product.service.IProductService;
import me.davidgarmo.soundseeker.product.service.dto.ProductDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {
    private final IProductService productService;

    @Autowired
    public ProductController(IProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<ProductDto> save(@Valid @RequestBody ProductDto productDto) {
        return ResponseEntity.status(201).body(this.productService.save(productDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(this.productService.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<ProductDto>> findAll() {
        return ResponseEntity.ok(this.productService.findAll());
    }

    @PutMapping
    public ResponseEntity<ProductDto> update(@Valid @RequestBody ProductDto productDto) {
        return ResponseEntity.ok(this.productService.update(productDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        this.productService.deleteById(id);
        return ResponseEntity.status(204).build();
    }
}
