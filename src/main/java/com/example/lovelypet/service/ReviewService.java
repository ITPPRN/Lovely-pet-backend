package com.example.lovelypet.service;

import com.example.lovelypet.entity.Hotel;
import com.example.lovelypet.entity.Review;
import com.example.lovelypet.entity.User;
import com.example.lovelypet.exception.BaseException;
import com.example.lovelypet.exception.ReviewException;
import com.example.lovelypet.repository.ReviewRepository;
import org.springframework.stereotype.Service;

import java.util.List;
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
        if (rating == 0.0) {
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

    //find
    public List<Review> findByHotelId(Hotel hotel) {
        return reviewRepository.findByHotelId(hotel);
    }

    //calculate
    public float calculateAverageScore(Hotel hotel) {
        List<Review> reviews = reviewRepository.findByHotelId(hotel);
        int totalReviews = reviews.size();
        if (totalReviews == 0) {
            return 0.0F;
        }

        float sumScores = 0.0F;
        for (Review review : reviews) {
            sumScores += review.getRating();
        }

        return sumScores / totalReviews;
    }

}
