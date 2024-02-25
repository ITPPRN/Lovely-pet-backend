package com.example.lovelypet.repository;

import com.example.lovelypet.entity.Booking;
import com.example.lovelypet.entity.BookingByClinic;
import com.example.lovelypet.entity.Hotel;
import com.example.lovelypet.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface BookingByClinicRepository extends CrudRepository<BookingByClinic, String> {
    Optional<BookingByClinic> findById(int id);
    List<BookingByClinic> findByHotelIdAndStatusBooking(int hotel,String state);

    void deleteById(int id);
}