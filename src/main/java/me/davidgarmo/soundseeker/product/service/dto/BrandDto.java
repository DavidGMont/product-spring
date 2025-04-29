package me.davidgarmo.soundseeker.product.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import me.davidgarmo.soundseeker.product.persistence.entity.BrandEntity;

import java.io.Serializable;

/**
 * DTO for {@link BrandEntity}
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record BrandDto(
        Long id,

        @Size(message = "Brand name cannot exceed 60 characters.", max = 60)
        @NotBlank(message = "Brand name cannot be null or empty.")
        String name,

        @Size(message = "Brand description cannot exceed 1000 characters.", max = 1000)
        String description,

        @Size(message = "Brand thumbnail cannot exceed 1000 characters.", max = 1000)
        String thumbnail,

        @NotNull(message = "Brand availability cannot be null.")
        Boolean available) implements Serializable {
}