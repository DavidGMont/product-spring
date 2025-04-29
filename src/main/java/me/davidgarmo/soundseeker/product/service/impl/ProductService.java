package me.davidgarmo.soundseeker.product.service.impl;

import me.davidgarmo.soundseeker.product.persistence.entity.ProductEntity;
import me.davidgarmo.soundseeker.product.persistence.mapper.ProductMapper;
import me.davidgarmo.soundseeker.product.persistence.repository.ProductRepository;
import me.davidgarmo.soundseeker.product.service.ICrudService;
import me.davidgarmo.soundseeker.product.service.dto.ProductDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
        if (this.productRepository.existsByNameIgnoreCase(productDto.name())) {
            throw new IllegalArgumentException("Product name already exists.");
        }
        ProductEntity productEntity = this.productMapper.toEntity(productDto);
        ProductEntity savedProduct = this.productRepository.save(productEntity);
        return this.productMapper.toDto(savedProduct);
    }

    @Override
    public ProductDto findById(Long id) {
        return this.productRepository.findById(id)
                .map(productMapper::toDto)
                .orElse(null);
    }

    @Override
    public List<ProductDto> findAll() {
        return this.productRepository.findAll()
                .stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public ProductDto update(ProductDto productDto) {
        if (productDto.id() == null) {
            throw new IllegalArgumentException("Product ID cannot be null.");
        }
        if (this.productRepository.existsByNameIgnoreCaseAndIdNot(productDto.name(), productDto.id())) {
            throw new IllegalArgumentException("Product name already exists.");
        }
        ProductEntity productToUpdate = this.productMapper.toEntity(productDto);
        ProductEntity updatedProduct = this.productRepository.save(productToUpdate);
        return this.productMapper.toDto(updatedProduct);
    }

    @Override
    public void deleteById(Long id) {

    }
}
