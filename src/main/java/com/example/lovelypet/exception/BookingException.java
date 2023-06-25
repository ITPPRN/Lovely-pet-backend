package com.example.lovelypet.exception;

public class BookingException extends BaseException {
    public BookingException(String code) {
        super("booking." + code);
    }

    //CREATE
    public static BookingException createUserIdNull() {
        return new BookingException("create.userId.null");
    }

    public static BookingException createHotelIdNull() {
        return new BookingException("create.hotelId.null");
    }

    public static BookingException createRoomIdNull() {
        return new BookingException("create.roomId.null");
    }

    public static BookingException createPetIdNull() {
        return new BookingException("create.petId.null");
    }

    public static BookingException createBookingStartDateNull() {
        return new BookingException("create.bookingStartDate.null");
    }

    public static BookingException createBookingEndDateNull() {
        return new BookingException("create.bookingEndDate.null");
    }

    public static BookingException createBookingDateNull() {
        return new BookingException("create.bookingDate.null");
    }

}
