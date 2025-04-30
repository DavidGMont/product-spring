package me.davidgarmo.soundseeker.product.service.impl;

import me.davidgarmo.soundseeker.product.persistence.entity.BrandEntity;
import me.davidgarmo.soundseeker.product.persistence.mapper.BrandMapper;
import me.davidgarmo.soundseeker.product.persistence.repository.BrandRepository;
import me.davidgarmo.soundseeker.product.service.IBrandService;
import me.davidgarmo.soundseeker.product.service.dto.BrandDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BrandService implements IBrandService {
    private final BrandRepository brandRepository;
    private final BrandMapper brandMapper;

    public BrandService(BrandRepository brandRepository, BrandMapper brandMapper) {
        this.brandRepository = brandRepository;
        this.brandMapper = brandMapper;
    }

    @Override
    public BrandDto save(BrandDto brandDto) {
        if (this.brandRepository.existsByNameIgnoreCase(brandDto.name())) {
            throw new IllegalArgumentException("Brand name already exists.");
        }
        BrandEntity brand = this.brandMapper.toEntity(brandDto);
        BrandEntity savedBrand = this.brandRepository.save(brand);
        return this.brandMapper.toDto(savedBrand);
    }

    @Override
    public BrandDto findById(Long id) {
        return this.brandRepository.findById(id)
                .map(brandMapper::toDto)
                .orElseThrow(() -> new IllegalArgumentException("Brand not found."));
    }

    @Override
    public List<BrandDto> findAll() {
        return this.brandRepository.findAll()
                .stream()
                .map(brandMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public BrandDto update(BrandDto brandDto) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }
}
