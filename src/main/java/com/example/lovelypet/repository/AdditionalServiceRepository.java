package com.example.lovelypet.repository;

import com.example.lovelypet.entity.AdditionalServices;
import com.example.lovelypet.entity.Hotel;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface AdditionalServiceRepository extends CrudRepository<AdditionalServices, String> {

    Optional<AdditionalServices> findById(int id);

    List<AdditionalServices> findByHotelId(Hotel hotel);

    void deleteById(int id);
}