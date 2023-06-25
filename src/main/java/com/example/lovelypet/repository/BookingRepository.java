package com.example.lovelypet.repository;

import com.example.lovelypet.entity.Booking;
import org.springframework.data.repository.CrudRepository;

public interface BookingRepository extends CrudRepository<Booking, String> {

}