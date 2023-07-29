package com.example.lovelypet.service;

import com.example.lovelypet.entity.Pet;
import com.example.lovelypet.entity.PetType;
import com.example.lovelypet.entity.User;
import com.example.lovelypet.exception.BaseException;
import com.example.lovelypet.repository.PetRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PetService {

    private final PetRepository repository;

    public PetService(PetRepository repository) {
        this.repository = repository;
    }

    public List<Pet> findByPetTypeId(PetType petType) {
        return repository.findByPetTypeId(petType);
    }

    public List<Pet> findByUserId(User user) {
        return repository.findByUserId(user);
    }

    public Optional<Pet> findById(int id) {
        return repository.findById(id);
    }

    public Optional<Pet> findByIdAndUserId(int id, User userId) {
        return repository.findByIdAndUserId(id, userId);
    }

    public Pet create(
            User user,
            String petName,
            String petPhoto,
            PetType petType,
            Date birthday
    ) {
        Pet entity = new Pet();
        entity.setUserId(user);
        entity.setPetTypeId(petType);
        entity.setPetName(petName);
        entity.setPetPhoto(petPhoto);
        entity.setBirthday(birthday);
        return repository.save(entity);
    }

    public Pet update(Pet pet) throws BaseException {
        return repository.save(pet);
    }

}
