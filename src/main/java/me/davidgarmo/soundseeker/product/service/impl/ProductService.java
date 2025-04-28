package me.davidgarmo.soundseeker.product.service.impl;

import me.davidgarmo.soundseeker.product.persistence.entity.ProductEntity;
import me.davidgarmo.soundseeker.product.persistence.mapper.ProductMapper;
import me.davidgarmo.soundseeker.product.persistence.repository.ProductRepository;
import me.davidgarmo.soundseeker.product.service.ICrudService;
import me.davidgarmo.soundseeker.product.service.dto.ProductDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService implements ICrudService<ProductDto> {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Autowired
    public ProductService(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    @Override
    public ProductDto save(ProductDto productDto) {
        return null;
    }

    @Override
    public ProductDto findById(Long id) {
        return null;
    }

    @Override
    public List<ProductDto> findAll() {
        return List.of();
    }

    @Override
    public ProductDto update(ProductDto productDto) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }
}
