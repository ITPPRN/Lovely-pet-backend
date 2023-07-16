package com.example.lovelypet.service;

import com.example.lovelypet.entity.*;
import com.example.lovelypet.exception.BaseException;
import com.example.lovelypet.exception.PhotoRoomException;
import com.example.lovelypet.exception.ServiceHistoryException;
import com.example.lovelypet.repository.ServiceHistoryRepository;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class ServiceHistoryService {

    private final ServiceHistoryRepository serviceHistoryRepository;

    public ServiceHistoryService(ServiceHistoryRepository serviceHistoryRepository) {
        this.serviceHistoryRepository = serviceHistoryRepository;
    }

    public ServiceHistory create(
            Hotel hotelId,
            User userId,
            Booking bookingId
    ) throws BaseException {

        //validate
        if (Objects.isNull(hotelId)) {
            throw ServiceHistoryException.createHotelIdNull();
        }

        if (Objects.isNull(userId)) {
            throw ServiceHistoryException.createUserIdNull();
        }

        if (Objects.isNull(bookingId)) {
            throw ServiceHistoryException.createBookingIdNull();
        }

        //verify


        ServiceHistory entity = new ServiceHistory();
        entity.setHotelId(hotelId);
        entity.setUserId(userId);
        entity.setBookingId(bookingId);
        return serviceHistoryRepository.save(entity);
    }

}
