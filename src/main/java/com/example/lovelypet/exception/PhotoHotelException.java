package com.example.lovelypet.exception;

public class PhotoHotelException extends BaseException {
    public PhotoHotelException(String code) {
        super("booking." + code);
    }

    //CREATE
    public static PhotoHotelException createPartFileNull() {
        return new PhotoHotelException("create.part.file.null");
    }

    public static PhotoHotelException createHotelIdNull() {
        return new PhotoHotelException("create.hotel.id.null");
    }

    public static PhotoHotelException imageOverload() {
        return new PhotoHotelException("image.hotel.overload");
    }

    public static PhotoHotelException notFound() {
        return new PhotoHotelException("image.not.found");
    }

}
