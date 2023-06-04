package com.example.lovelypet.exception;

public class ClinicException extends BaseException {
    public ClinicException(String code) {
        super("clinic." + code);
    }

    public static ClinicException createUserNameNull() {
        return new ClinicException("register.userName.null");
    }

    public static ClinicException createPassWordNull() {
        return new ClinicException("register.passWord.null");
    }

    public static ClinicException createNameNull() {
        return new ClinicException("register.name.null");
    }

    public static ClinicException createTelNull() {
        return new ClinicException("register.tel.null");
    }

    public static ClinicException createEmailNull() {
        return new ClinicException("register.email.null");
    }

    public static ClinicException createAddressNull() {
        return new ClinicException("register.address.null");
    }

    public static ClinicException createPhotoNull() {
        return new ClinicException("register.photo.null");
    }

    public static ClinicException createLicenseNull() {
        return new ClinicException("register.license.null");
    }

    public static ClinicException createEmailDuplicated() {
        return new ClinicException("create.email.duplicated");
    }

    public static ClinicException createUserNameDuplicated() {
        return new ClinicException("create.userName.duplicated");
    }

    public static ClinicException createLicenseDuplicated() {
        return new ClinicException("create.license.duplicated");
    }


    //LOGIN
    public static ClinicException loginFailUserNameNotFound() {
        return new ClinicException("login.fail");
    }

    public static ClinicException loginFailPasswordIncorrect() {
        return new ClinicException("login.fail");
    }
}
