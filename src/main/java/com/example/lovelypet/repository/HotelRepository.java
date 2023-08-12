package com.example.lovelypet.repository;

import com.example.lovelypet.entity.Hotel;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface HotelRepository extends CrudRepository<Hotel, String> {

    boolean existsByHotelUsername(String hotelUsername);

    boolean existsByEmail(String email);

    Optional<Hotel> findByHotelUsername(String hotelUsername);

    Optional<Hotel> findById(int idU);

    Optional<Hotel> findByToken(String token);

    List<Hotel> findByOpenStateAndVerifyOrderByRatingDesc(String stateOpen, String stateVerify);

    List<Hotel> findByVerify(String stateVerify);

    void deleteById(int id);

    Optional<Hotel> findByEmail(String email);

}
