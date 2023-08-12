package com.example.lovelypet.repository;

import com.example.lovelypet.entity.Hotel;
import com.example.lovelypet.entity.Review;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ReviewRepository extends CrudRepository<Review, String> {
    List<Review> findByHotelId(Hotel hotel);
}