package me.davidgarmo.soundseeker.product.persistence.entity;

import jakarta.persistence.*;
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
    private String name;

    @Column(length = 1000, nullable = false)
    private String description;

    @Column(columnDefinition = "DECIMAL(10, 2)", nullable = false)
    private Double price;

    @Column(columnDefinition = "TINYINT(1)", nullable = false)
    private Boolean available;

    @Column(columnDefinition = "VARCHAR(1000)", nullable = false)
    private String thumbnail;
}
