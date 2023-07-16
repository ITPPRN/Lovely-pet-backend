package com.example.lovelypet.service;

import com.example.lovelypet.entity.Hotel;
import com.example.lovelypet.entity.Review;
import com.example.lovelypet.entity.User;
import com.example.lovelypet.exception.BaseException;
import com.example.lovelypet.exception.ReviewException;
import com.example.lovelypet.repository.ReviewRepository;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;

    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }


    public Review create(
            float rating,
            String comment,
            Hotel hotelId,
            User userId
    ) throws BaseException {

        //validate
        if (Objects.isNull(rating)) {
            throw ReviewException.createRatingNull();
        }

        if (Objects.isNull(hotelId)) {
            throw ReviewException.createHotelIdNull();
        }

        if (Objects.isNull(userId)) {
            throw ReviewException.createUserIdNull();
        }

        //verify


        Review entity = new Review();
        entity.setRating(rating);
        if (!Objects.isNull(comment)) {
            entity.setComment(comment);
        }
        entity.setUserId(userId);
        entity.setHotelId(hotelId);
        return reviewRepository.save(entity);
    }

}
