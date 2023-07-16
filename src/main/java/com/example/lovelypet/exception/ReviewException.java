package com.example.lovelypet.exception;

public class ReviewException extends BaseException {
    public ReviewException(String code) {
        super("booking." + code);
    }

    //CREATE
    public static ReviewException createRatingNull() {
        return new ReviewException("create.rating.null");
    }
    public static ReviewException createHotelIdNull() {
        return new ReviewException("create.hotel.id.null");
    }
    public static ReviewException createUserIdNull() {
        return new ReviewException("create.user.id.null");
    }


}
