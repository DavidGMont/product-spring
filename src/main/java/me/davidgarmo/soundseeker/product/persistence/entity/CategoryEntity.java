package me.davidgarmo.soundseeker.product.persistence.entity;

import jakarta.persistence.*;
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
    private String name;

    @Column(length = 1000)
    private String description;

    @Column(length = 1000)
    private String thumbnail;

    @Column(columnDefinition = "TINYINT", nullable = false)
    private Boolean available = true;
}
