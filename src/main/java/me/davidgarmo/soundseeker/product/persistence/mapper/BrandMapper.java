package me.davidgarmo.soundseeker.product.persistence.mapper;

import me.davidgarmo.soundseeker.product.persistence.entity.BrandEntity;
import me.davidgarmo.soundseeker.product.service.dto.BrandDto;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface BrandMapper {
    BrandEntity toEntity(BrandDto brandDto);

    BrandDto toDto(BrandEntity brandEntity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    BrandEntity partialUpdate(BrandDto brandDto, @MappingTarget BrandEntity brandEntity);
}