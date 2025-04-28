package me.davidgarmo.soundseeker.product.service.impl;

import me.davidgarmo.soundseeker.product.persistence.entity.ProductEntity;
import me.davidgarmo.soundseeker.product.service.ICrudService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService implements ICrudService<ProductEntity> {
    @Override
    public ProductEntity save(ProductEntity productEntity) {
        return null;
    }

    @Override
    public ProductEntity findById(Long id) {
        return null;
    }

    @Override
    public List<ProductEntity> findAll() {
        return List.of();
    }

    @Override
    public ProductEntity update(ProductEntity productEntity) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }
}
