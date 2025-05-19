package me.davidgarmo.soundseeker.product.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serializable;

/**
 * DTO for {@link me.davidgarmo.soundseeker.product.persistence.entity.CategoryEntity}
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record CategoryDto(
        Long id,

        @Size(message = "Category name cannot exceed 60 characters.", max = 60)
        @NotBlank(message = "Category name cannot be null or empty.")
        String name,

        @Size(message = "Category description cannot exceed 1000 characters.", max = 1000)
        String description,

        @Size(message = "Category thumbnail cannot exceed 1000 characters.", max = 1000)
        String thumbnail,

        @NotNull(message = "Category availability cannot be null.")
        Boolean available) implements Serializable {
}