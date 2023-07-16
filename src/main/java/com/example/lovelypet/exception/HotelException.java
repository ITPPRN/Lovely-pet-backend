package com.example.lovelypet.exception;

public class HotelException extends BaseException {
    public HotelException(String code) {
        super("hotel." + code);
    }

    public static HotelException createUserNameNull() {
        return new HotelException("register.userName.null");
    }

    public static HotelException createPassWordNull() {
        return new HotelException("register.passWord.null");
    }

    public static HotelException createNameNull() {
        return new HotelException("register.name.null");
    }

    public static HotelException createTelNull() {
        return new HotelException("register.tel.null");
    }

    public static HotelException createEmailNull() {
        return new HotelException("register.email.null");
    }

    public static HotelException createAddressNull() {
        return new HotelException("register.address.null");
    }

    public static HotelException createPhotoNull() {
        return new HotelException("register.photo.null");
    }

    public static HotelException createLicenseNull() {
        return new HotelException("register.license.null");
    }

    public static HotelException createEmailDuplicated() {
        return new HotelException("create.email.duplicated");
    }

    public static HotelException createUserNameDuplicated() {
        return new HotelException("create.userName.duplicated");
    }

    public static HotelException createLicenseDuplicated() {
        return new HotelException("create.license.duplicated");
    }


    //LOGIN
    public static HotelException loginFailUserNameNotFound() {
        return new HotelException("login.fail");
    }

    public static HotelException loginFailPasswordIncorrect() {
        return new HotelException("login.fail");
    }

    //find
    public static HotelException notFound() {
        return new HotelException("hotel.not.found");
    }
}
