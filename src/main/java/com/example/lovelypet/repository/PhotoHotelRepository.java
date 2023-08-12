package com.example.lovelypet.repository;

import com.example.lovelypet.entity.Hotel;
import com.example.lovelypet.entity.PhotoHotel;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface PhotoHotelRepository extends CrudRepository<PhotoHotel, String> {
    Optional<PhotoHotel> findById(int idU);

    Optional<PhotoHotel> findByPhotoHotelFile(String name);

    void deleteById(int id);

    List<PhotoHotel> findByHotelId(Hotel hotel);
}