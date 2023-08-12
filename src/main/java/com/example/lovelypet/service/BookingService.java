package com.example.lovelypet.service;

import com.example.lovelypet.entity.*;
import com.example.lovelypet.exception.BaseException;
import com.example.lovelypet.exception.BookingException;
import com.example.lovelypet.repository.BookingRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

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
            LocalDateTime date,
            String paymentMethod,
            String payment

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
        entity.setPaymentMethod(paymentMethod);
        if (!paymentMethod.equals("cash payment")) {
            if (Objects.isNull(payment)) {
                throw BookingException.createBookingPaymentNull();
            }
            entity.setPayment(payment);
        } else {
            if (Objects.nonNull(payment)) {
                throw BookingException.wrongPaymentMethod();
            }
        }
        entity.setState("waite");
        return bookingRepository.save(entity);
    }

    //find
    public Optional<Booking> findById(int id) {
        return bookingRepository.findById(id);
    }

    public List<Booking> findByIdHotelAndState(Hotel hotel, String state) {
        return bookingRepository.findByHotelIdAndState(hotel, state);
    }

    public List<Booking> findByIdHotel(Hotel hotel) {
        return bookingRepository.findByHotelId(hotel);
    }

    //update
    public Booking updateBooking(Booking booking) throws BookingException {
        if (Objects.isNull(booking)) {
            throw BookingException.updateObjectIsNull();
        }
        return bookingRepository.save(booking);
    }

    //delete
    public void deleteById(int id) {
        bookingRepository.deleteById(id);
    }

    ////////////////////////////////////////////////////
    ////////////////////////////////////////////////////

}
