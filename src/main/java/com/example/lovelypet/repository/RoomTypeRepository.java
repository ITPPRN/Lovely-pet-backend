package com.example.lovelypet.repository;

import com.example.lovelypet.entity.PetType;
import com.example.lovelypet.entity.RoomType;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RoomTypeRepository extends CrudRepository<RoomType, String> {

    Optional<RoomType> findByName(String name);

    boolean existsByName(String name);
}