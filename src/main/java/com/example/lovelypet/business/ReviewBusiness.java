package com.example.lovelypet.business;

import com.example.lovelypet.entity.Hotel;
import com.example.lovelypet.entity.Review;
import com.example.lovelypet.entity.User;
import com.example.lovelypet.exception.BaseException;
import com.example.lovelypet.exception.HotelException;
import com.example.lovelypet.exception.UserException;
import com.example.lovelypet.model.ReviewRequest;
import com.example.lovelypet.model.ReviewResponse;
import com.example.lovelypet.service.HotelService;
import com.example.lovelypet.service.ReviewService;
import com.example.lovelypet.service.UserService;
import com.example.lovelypet.util.SecurityUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class ReviewBusiness {

    private final HotelService hotelService;

    private final UserService userService;

    private final ReviewService reviewService;

    public ReviewBusiness(HotelService hotelService, UserService userService, ReviewService reviewService) {
        this.hotelService = hotelService;
        this.userService = userService;
        this.reviewService = reviewService;
    }

    public String satisfaction(ReviewRequest request) throws BaseException {
        //fetch idUser from token
        Optional<String> opt = SecurityUtil.getCurrentUserId();
        if (opt.isEmpty()) {
            throw UserException.unauthorized();
        }
        String idUser = opt.get();

        //user
        Optional<User> optUser = userService.findById(Integer.parseInt(idUser));
        if (optUser.isEmpty()) {
            throw UserException.notFound();
        }
        User user = optUser.get();

        //hotel
        Optional<Hotel> optHotel = hotelService.findById(request.getIdHotel());
        if (optHotel.isEmpty()) {
            throw HotelException.notFound();
        }
        Hotel hotel = optHotel.get();

        reviewService.create(request.getRating(), request.getComment(), hotel, user);
        float rating = reviewService.calculateAverageScore(hotel);
        hotel.setRating(rating);
        hotelService.update(hotel);
        return "Thank you for your feedback.";
    }

    //get data in review
    public List<ReviewResponse> listReview(ReviewRequest id) throws BaseException {
        Optional<Hotel> optHotel = hotelService.findById(id.getId());
        if (optHotel.isEmpty()) {
            HotelException.notFound();
        }
        Hotel hotel = optHotel.get();
        List<Review> reviews = reviewService.findByHotelId(hotel);
        return resList(reviews);

    }

    public List<ReviewResponse> resList(List<Review> reviews) {
        List<ReviewResponse> response = new ArrayList<>();
        for (Review review : reviews) {
            ReviewResponse data = new ReviewResponse();
            data.setId(review.getId());
            data.setComment(review.getComment());
            data.setRating(review.getRating());
            data.setUserId(review.getUserId().getId());
            data.setHotelId(review.getHotelId().getId());
            response.add(data);
        }
        return response;
    }


    /////////////////////////////////////////////////////////

    /////////////////////////////////////////////////////////
}




