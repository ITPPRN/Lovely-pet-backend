package com.example.lovelypet.repository;

import com.example.lovelypet.entity.Hotel;
import com.example.lovelypet.entity.ServiceHistory;
import com.example.lovelypet.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ServiceHistoryRepository extends CrudRepository<ServiceHistory, String> {
    List<ServiceHistory> findByHotelId(Hotel hotel);

    List<ServiceHistory> findByUserId(User user);

    Optional<ServiceHistory> findById(int id);
}