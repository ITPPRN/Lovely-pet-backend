package com.example.lovelypet.repository;

import com.example.lovelypet.entity.PetType;
import com.example.lovelypet.entity.Verify;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface PetTypeRepository extends CrudRepository<PetType, String> {

}