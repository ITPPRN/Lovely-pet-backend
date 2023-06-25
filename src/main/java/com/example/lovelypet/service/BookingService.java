package com.example.lovelypet.service;

import com.example.lovelypet.entity.*;
import com.example.lovelypet.exception.BaseException;
import com.example.lovelypet.exception.BookingException;
import com.example.lovelypet.repository.BookingRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;

    public BookingService(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    public Booking create(
            User user,
            Hotel hotel,
            Room room,
            Pet pet,
            Date bookingStartDate,
            Date bookingEndDate,
            LocalDateTime date

    ) throws BaseException {

        //validate
        if (Objects.isNull(user)) {
            throw BookingException.createUserIdNull();
        }

        if (Objects.isNull(hotel)) {
            throw BookingException.createHotelIdNull();
        }

        if (Objects.isNull(room)) {
            throw BookingException.createRoomIdNull();
        }

        if (Objects.isNull(pet)) {
            throw BookingException.createPetIdNull();
        }

        if (Objects.isNull(bookingStartDate)) {
            throw BookingException.createBookingStartDateNull();
        }

        if (Objects.isNull(bookingEndDate)) {
            throw BookingException.createBookingEndDateNull();
        }

        if (Objects.isNull(date)) {
            throw BookingException.createBookingDateNull();
        }

        //verify


        Booking entity = new Booking();
        entity.setUserId(user);
        entity.setHotelId(hotel);
        entity.setRoomId(room);
        entity.setPetId(pet);
        entity.setBookingStartDate(bookingStartDate);
        entity.setBookingEndDate(bookingEndDate);
        entity.setDate(date);
        return bookingRepository.save(entity);
    }

}
