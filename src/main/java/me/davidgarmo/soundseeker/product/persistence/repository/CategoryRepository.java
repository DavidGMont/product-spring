package me.davidgarmo.soundseeker.product.persistence.repository;

import jdk.jfr.Registered;
import me.davidgarmo.soundseeker.product.persistence.entity.CategoryEntity;
import org.springframework.data.repository.ListCrudRepository;

@Registered
public interface CategoryRepository extends ListCrudRepository<CategoryEntity, Long> {
}