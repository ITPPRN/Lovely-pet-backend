package com.example.lovelypet.repository;

import com.example.lovelypet.entity.Booking;
import com.example.lovelypet.entity.Hotel;
import com.example.lovelypet.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface BookingRepository extends CrudRepository<Booking, String> {
    Optional<Booking> findById(int id);

    List<Booking> findByHotelIdAndState(Hotel hotel, String state);

    List<Booking> findByHotelId(Hotel hotel);

    List<Booking> findByUserId(User user);

    void deleteById(int id);
}