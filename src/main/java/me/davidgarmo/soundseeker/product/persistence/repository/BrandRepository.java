package me.davidgarmo.soundseeker.product.persistence.repository;

import me.davidgarmo.soundseeker.product.persistence.entity.BrandEntity;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BrandRepository extends ListCrudRepository<BrandEntity, Long> {
    boolean existsByNameIgnoreCase(String name);
}
