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
import org.hibernate.proxy.HibernateProxy;

import java.util.Locale;
import java.util.Objects;

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

    @Column(columnDefinition = "TINYINT", nullable = false)
    @NotNull(message = "Product availability cannot be null.")
    private Boolean available;

    @Column(columnDefinition = "VARCHAR(1000)", nullable = false)
    @NotBlank(message = "Product thumbnail cannot be null or empty.")
    private String thumbnail;

    @ManyToOne(optional = false)
    @JoinColumn(name = "brand_id", nullable = false)
    private BrandEntity brand;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        ProductEntity that = (ProductEntity) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }

    @Override
    public String toString() {
        return String.format(Locale.ENGLISH,
                """
                        {
                            id: %s,
                            name: "%s",
                            description: "%s",
                            price: %.2f,
                            available: %s,
                            thumbnail: "%s"
                        }
                        """,
                id, name, description, price, available, thumbnail);
    }
}
