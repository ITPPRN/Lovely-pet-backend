package com.example.lovelypet.repository;

import com.example.lovelypet.entity.Pet;
import com.example.lovelypet.entity.PetType;
import com.example.lovelypet.entity.User;
import com.example.lovelypet.entity.Verify;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface PetTypeRepository extends CrudRepository<PetType, String> {

    Optional<PetType> findByName(String name);
    Optional<PetType> findById(int id);

    boolean existsByName(String name);
}