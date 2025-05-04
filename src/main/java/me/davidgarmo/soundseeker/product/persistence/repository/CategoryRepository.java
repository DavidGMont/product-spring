package me.davidgarmo.soundseeker.product.persistence.repository;

import me.davidgarmo.soundseeker.product.persistence.entity.CategoryEntity;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends ListCrudRepository<CategoryEntity, Long> {
    boolean existsByNameIgnoreCase(@NonNull String name);

    boolean existsByNameIgnoreCaseAndIdNot(@NonNull String name, @NonNull Long id);
}