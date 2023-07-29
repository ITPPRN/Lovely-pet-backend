package com.example.lovelypet.exception;

public class HotelException extends BaseException {
    public HotelException(String code) {
        super("hotel." + code);
    }

    public static HotelException createHotelIdNull() {
        return new HotelException("hotel.id.null");
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

    //ACTIVATE
    public static HotelException activateNoToken() {
        return new HotelException("activate.no.token");
    }

    public static HotelException activateAlready() {
        return new HotelException("activate.already");
    }
    public static HotelException activateFail() {
        return new HotelException("activate.fail");
    }
    public static HotelException activateTokenExpire() {
        return new HotelException("activate.token.expire");
    }

    //resend activation
    public static HotelException resendActivationEmailNoEmail() {
        return new HotelException("resend.activation.no.email");
    }

    public static HotelException resendActivationEmailNoEmailEmailNotFound() {
        return new HotelException("resend.activation.fail");
    }

    //LOGIN
    public static HotelException loginFailUserNameNotFound() {
        return new HotelException("login.fail");
    }

    public static HotelException loginFailPasswordIncorrect() {
        return new HotelException("login.fail");
    }

    public static HotelException loginFailUserUnactivated() {
        return new HotelException("login.fail.unactivated");
    }

    public static HotelException passwordIncorrect() {
        return new HotelException("password.incorrect");
    }

    public static HotelException unauthorized() {
        return new HotelException("unauthorized");
    }

    //find
    public static HotelException notFound() {
        return new HotelException("hotel.not.found");
    }
}
