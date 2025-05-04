package me.davidgarmo.soundseeker.product.persistence.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "CATEGORY")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CategoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(length = 60, nullable = false, unique = true)
    @NotBlank(message = "Category name cannot be null or empty.")
    @Size(max = 60, message = "Category name cannot exceed 60 characters.")
    private String name;

    @Column(length = 1000)
    @Size(max = 1000, message = "Category description cannot exceed 1000 characters.")
    private String description;

    @Column(length = 1000)
    @Size(max = 1000, message = "Category thumbnail cannot exceed 1000 characters.")
    private String thumbnail;

    @Column(columnDefinition = "TINYINT", nullable = false)
    @NotBlank(message = "Category availability cannot be null.")
    private Boolean available = true;
}
