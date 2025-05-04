package me.davidgarmo.soundseeker.product.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.io.Serializable;

/**
 * DTO for {@link me.davidgarmo.soundseeker.product.persistence.entity.ProductEntity}
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record ProductDto(
        Long id,

        @Size(message = "Product name cannot exceed 60 characters.", max = 60)
        @NotBlank(message = "Product name cannot be null or empty.")
        String name,

        @Size(message = "Product description cannot exceed 1000 characters.", max = 1000)
        @NotBlank(message = "Product description cannot be null or empty.")
        String description,

        @NotNull(message = "Price cannot be null.")
        @Positive(message = "Price must be positive and greater than zero.")
        Double price,

        @NotNull(message = "Product availability cannot be null.")
        Boolean available,

        @NotBlank(message = "Product thumbnail cannot be null or empty.")
        String thumbnail) implements Serializable {
}