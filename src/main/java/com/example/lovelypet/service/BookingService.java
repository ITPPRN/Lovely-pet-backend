package com.example.lovelypet.service;

import com.example.lovelypet.entity.*;
import com.example.lovelypet.exception.BaseException;
import com.example.lovelypet.exception.BookingException;
import com.example.lovelypet.model.BookingByClinicListResponse;
import com.example.lovelypet.repository.BookingByClinicRepository;
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

    private final BookingByClinicRepository bookingByClinicRepository;

    public BookingService(BookingRepository bookingRepository, BookingByClinicRepository bookingByClinicRepository) {
        this.bookingRepository = bookingRepository;
        this.bookingByClinicRepository = bookingByClinicRepository;
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
            AdditionalServices additionalServices,
            double totalPrice

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
        entity.setState("waite");
        if (Objects.nonNull(additionalServices)) {
            entity.setAdditionalServiceId(additionalServices);
        }
        entity.setTotalPrice(totalPrice);
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

    public List<Booking> findByIdUser(User user) {
        return bookingRepository.findByUserId(user);
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


    //booking by clinic
    ////////////////////////////////////////////////////

    ////////////////////////////////////////////////////


    public BookingByClinic createByClinic(
            LocalDateTime date,
            String paymentMethod,
            double totalPrice,
            String nameCustomer,
            String namePet,
            int roomId,
            String additionalServiceId,
            int hotelId,
            Date bookingStartDate,
            Date bookingEndDate

    ) throws BaseException {

        //validate
        if (Objects.isNull(paymentMethod)) {
            throw BookingException.createUserIdNull();
        }

        if (Objects.isNull(nameCustomer)) {
            throw BookingException.createHotelIdNull();
        }

        if (Objects.isNull(namePet)) {
            throw BookingException.createRoomIdNull();
        }

        if (Objects.isNull(additionalServiceId)) {
            throw BookingException.createPetIdNull();
        }

        if (roomId == 0) {
            throw BookingException.createBookingStartDateNull();
        }

        if (hotelId == 0) {
            throw BookingException.createBookingEndDateNull();
        }

        if (Objects.isNull(bookingStartDate)) {
            throw BookingException.createBookingDateNull();
        }

        if (Objects.isNull(bookingEndDate)) {
            throw BookingException.createBookingDateNull();
        }


        if (Objects.isNull(date)) {
            throw BookingException.createBookingDateNull();
        }
        //verify
        BookingByClinic entity = new BookingByClinic();
        entity.setPaymentMethod(paymentMethod);
        entity.setTotalPrice(totalPrice);
        entity.setNameCustomer(nameCustomer);
        entity.setNamePet(namePet);
        entity.setRoomId(roomId);
        entity.setAdditionalServiceId(additionalServiceId);
        entity.setHotelId(hotelId);
        entity.setStatusBooking("approve");
        entity.setDate(date);
        entity.setBookingStartDate(bookingStartDate);
        entity.setBookingEndDate(bookingEndDate);

        return bookingByClinicRepository.save(entity);
    }

    public List<BookingByClinic> getBookingByClinic(int id , String state){
        return bookingByClinicRepository.findByHotelIdAndStatusBooking(id,state);
    }

    public Optional<BookingByClinic> getBookingByClinicID(int id ){
        return bookingByClinicRepository.findById(id);
    }

    public BookingByClinic updateBookingByClinic(BookingByClinic req){
        return bookingByClinicRepository.save(req);
    }

}
