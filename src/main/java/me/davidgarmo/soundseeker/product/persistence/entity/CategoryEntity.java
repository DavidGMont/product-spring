package me.davidgarmo.soundseeker.product.persistence.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.proxy.HibernateProxy;

import java.util.Locale;
import java.util.Objects;

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

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        CategoryEntity that = (CategoryEntity) o;
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
                            "id": %d,
                            "name": "%s",
                            "description": "%s",
                            "thumbnail": "%s",
                            "available": %b
                        }
                        """,
                id, name, description, thumbnail, available);
    }
}
