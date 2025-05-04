package me.davidgarmo.soundseeker.product.persistence.mapper;

import me.davidgarmo.soundseeker.product.persistence.entity.ProductEntity;
import me.davidgarmo.soundseeker.product.service.dto.ProductDto;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProductMapper {
    ProductEntity toEntity(ProductDto productDto);

    ProductDto toDto(ProductEntity productEntity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    ProductEntity partialUpdate(ProductDto productDto, @MappingTarget ProductEntity productEntity);
}