package com.example.lovelypet.service;

import com.example.lovelypet.entity.*;
import com.example.lovelypet.exception.BaseException;
import com.example.lovelypet.exception.BookingException;
import com.example.lovelypet.repository.RoomRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;

@Service
public class RoomService {

    private final RoomRepository roomRepository;

    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }


    public Room create(
            int totalRoom

            ) throws BaseException {

        //validate


        //verify

        //create
        Room entity = new Room();

        return roomRepository.save(entity);
    }

//    public Room create(
//            int totalRoom,
//
//
//    ) throws BaseException {
//
//        //validate
//        if (Objects.isNull(user)) {
//            throw BookingException.createUserIdNull();
//        }
//
//        if (Objects.isNull(hotel)) {
//            throw BookingException.createHotelIdNull();
//        }
//
//        if (Objects.isNull(room)) {
//            throw BookingException.createRoomIdNull();
//        }
//
//        if (Objects.isNull(pet)) {
//            throw BookingException.createPetIdNull();
//        }
//
//        if (Objects.isNull(bookingStartDate)) {
//            throw BookingException.createBookingStartDateNull();
//        }
//
//        if (Objects.isNull(bookingEndDate)) {
//            throw BookingException.createBookingEndDateNull();
//        }
//
//        if (Objects.isNull(date)) {
//            throw BookingException.createBookingDateNull();
//        }
//
//        //verify
//
//
//        Room entity = new Room();
//
//        return roomRepository.save(entity);
//    }

}
