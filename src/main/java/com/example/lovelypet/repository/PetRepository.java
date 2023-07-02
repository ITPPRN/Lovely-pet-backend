package com.example.lovelypet.repository;

import com.example.lovelypet.entity.Pet;
import com.example.lovelypet.entity.PetType;
import com.example.lovelypet.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface PetRepository extends CrudRepository<Pet, String> {

    List<Pet> findByUserId(User user);

    List<Pet> findByPetTypeId(PetType petType);

    Optional<Pet> findByIdAndUserId(int id, User userId);

}