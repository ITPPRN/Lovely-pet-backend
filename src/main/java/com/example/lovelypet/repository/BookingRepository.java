package com.example.lovelypet.repository;

import com.example.lovelypet.entity.Booking;
import com.example.lovelypet.entity.Hotel;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface BookingRepository extends CrudRepository<Booking, String> {
    Optional<Booking> findById(int id);

    List<Booking> findByHotelIdAndState(Hotel hotel, String state);

    List<Booking> findByHotelId(Hotel hotel);

    void deleteById(int id);
}