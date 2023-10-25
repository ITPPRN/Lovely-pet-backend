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

    public static BookingException createTotalPriceNull() {
        return new BookingException("create.total.price.null");
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

    public static BookingException createBookingPaymentNull() {
        return new BookingException("create.payment.null");
    }

    public static BookingException wrongPaymentMethod() {
        return new BookingException("wrong.payment.method");
    }

    //find
    public static BookingException notFound() {
        return new BookingException("booking.not.found");
    }

    public static BookingException roomIsNotAvailable() {
        return new BookingException("booking.room.is.not.available");
    }

    //update
    public static BookingException updateObjectIsNull() {
        return new BookingException("booking.update.object.is.null");
    }

    //update
    public static BookingException updateFail() {
        return new BookingException("update.fail");
    }

    public static BookingException updatePaymentMethodFail() {
        return new BookingException("update.payment.method.fail");
    }

    //get
    public static BookingException idBookingIsNull() {
        return new BookingException("id.booking.is.null");
    }
}
