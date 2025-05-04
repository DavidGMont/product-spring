package me.davidgarmo.soundseeker.product.service;

import java.util.List;

public interface ICrudService<T> {
    T save(T t);

    T findById(Long id);

    List<T> findAll();

    T update(T t);

    void deleteById(Long id);
}
