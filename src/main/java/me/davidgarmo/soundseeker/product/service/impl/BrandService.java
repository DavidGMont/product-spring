package me.davidgarmo.soundseeker.product.service.impl;

import me.davidgarmo.soundseeker.product.service.IBrandService;
import me.davidgarmo.soundseeker.product.service.dto.BrandDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BrandService implements IBrandService {
    @Override
    public BrandDto save(BrandDto brandDto) {
        return null;
    }

    @Override
    public BrandDto findById(Long id) {
        return null;
    }

    @Override
    public List<BrandDto> findAll() {
        return List.of();
    }

    @Override
    public BrandDto update(BrandDto brandDto) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }
}
