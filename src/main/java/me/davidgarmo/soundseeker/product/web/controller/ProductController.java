package me.davidgarmo.soundseeker.product.web.controller;

import jakarta.validation.Valid;
import me.davidgarmo.soundseeker.product.service.IProductService;
import me.davidgarmo.soundseeker.product.service.dto.ProductDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/products")
@CrossOrigin
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
}
