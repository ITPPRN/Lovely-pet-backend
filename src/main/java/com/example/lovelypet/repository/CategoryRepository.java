package com.example.lovelypet.repository;

import com.example.lovelypet.entity.Category;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends CrudRepository<Category, String> {
    boolean existsByName(String name);


}