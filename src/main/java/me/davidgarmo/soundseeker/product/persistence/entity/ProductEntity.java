package me.davidgarmo.soundseeker.product.persistence.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "PRODUCT")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(length = 60, nullable = false, unique = true)
    @NotBlank(message = "Product name cannot be null or empty.")
    @Size(max = 60, message = "Product name cannot exceed 60 characters.")
    private String name;

    @Column(length = 1000, nullable = false)
    @NotBlank(message = "Product description cannot be null or empty.")
    @Size(max = 1000, message = "Product description cannot exceed 1000 characters.")
    private String description;

    @Column(columnDefinition = "DECIMAL(10, 2)", nullable = false)
    @NotNull(message = "Price cannot be null.")
    @Positive(message = "Price must be positive and greater than zero.")
    private Double price;

    @Column(columnDefinition = "TINYINT(1)", nullable = false)
    @NotNull(message = "Product availability cannot be null.")
    private Boolean available;

    @Column(columnDefinition = "VARCHAR(1000)", nullable = false)
    @NotBlank(message = "Product thumbnail cannot be null or empty.")
    private String thumbnail;
}
