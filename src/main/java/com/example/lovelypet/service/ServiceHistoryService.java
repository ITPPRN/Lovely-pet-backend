package com.example.lovelypet.service;

import com.example.lovelypet.entity.Booking;
import com.example.lovelypet.entity.Hotel;
import com.example.lovelypet.entity.ServiceHistory;
import com.example.lovelypet.entity.User;
import com.example.lovelypet.exception.BaseException;
import com.example.lovelypet.exception.ServiceHistoryException;
import com.example.lovelypet.repository.ServiceHistoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

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

    //find
    public List<ServiceHistory> findByHotelId(Hotel hotel) {
        return serviceHistoryRepository.findByHotelId(hotel);
    }

    public List<ServiceHistory> findByUserId(User user) {
        return serviceHistoryRepository.findByUserId(user);
    }

    public Optional<ServiceHistory> findById(int id) {
        return serviceHistoryRepository.findById(id);
    }

}
