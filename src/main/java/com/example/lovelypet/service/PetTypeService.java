package com.example.lovelypet.service;

import com.example.lovelypet.entity.Pet;
import com.example.lovelypet.entity.PetType;
import com.example.lovelypet.entity.User;
import com.example.lovelypet.repository.PetRepository;
import com.example.lovelypet.repository.PetTypeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PetTypeService {

   private final PetTypeRepository repository;

    public PetTypeService(PetTypeRepository repository) {
        this.repository = repository;
    }

    public PetType create(String petTypeName){

        PetType entity = new PetType();
        entity.setName(petTypeName);
        return repository.save(entity);
    }

    public Optional<PetType> findByName(String name) {

        return repository.findByName(name);
    }

    public Optional<PetType> findByIdPet(int id) {

        return repository.findById(id);
    }
}
