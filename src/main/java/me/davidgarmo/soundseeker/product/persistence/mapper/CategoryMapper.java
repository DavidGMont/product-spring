package me.davidgarmo.soundseeker.product.persistence.mapper;

import me.davidgarmo.soundseeker.product.persistence.entity.CategoryEntity;
import me.davidgarmo.soundseeker.product.service.dto.CategoryDto;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface CategoryMapper {
    CategoryEntity toEntity(CategoryDto categoryDto);

    CategoryDto toDto(CategoryEntity categoryEntity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    CategoryEntity partialUpdate(CategoryDto categoryDto, @MappingTarget CategoryEntity categoryEntity);
}