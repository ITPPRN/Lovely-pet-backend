package com.example.lovelypet.repository;

import com.example.lovelypet.entity.Review;
import org.springframework.data.repository.CrudRepository;

public interface ReviewRepository extends CrudRepository<Review, String> {

}