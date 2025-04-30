package me.davidgarmo.soundseeker.product.web.controller;

import jakarta.validation.Valid;
import me.davidgarmo.soundseeker.product.service.IBrandService;
import me.davidgarmo.soundseeker.product.service.dto.BrandDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}
