package me.davidgarmo.soundseeker.product.persistence.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "BRAND")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BrandEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(length = 60, nullable = false, unique = true)
    @NotBlank(message = "Brand name cannot be null or empty.")
    @Size(max = 60, message = "Brand name cannot exceed 60 characters.")
    private String name;

    @Column(length = 1000)
    @Size(max = 1000, message = "Brand description cannot exceed 1000 characters.")
    private String description;

    @Column(length = 1000)
    @Size(max = 1000, message = "Brand thumbnail cannot exceed 1000 characters.")
    private String thumbnail;

    @Column(columnDefinition = "TINYINT", nullable = false)
    @NotNull(message = "Brand availability cannot be null.")
    private Boolean available = true;
}
