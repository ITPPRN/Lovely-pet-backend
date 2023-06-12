package com.example.lovelypet.exception;

public class VerifyException extends BaseException {
    public VerifyException(String code) {
        super("verifier." + code);
    }

    public static VerifyException requestNull() {
        return new VerifyException("register.request.null");
    }

    public static VerifyException notFound() {
        return new VerifyException("verifier.not.found");
    }

    public static VerifyException unauthorized() {
        return new VerifyException("unauthorized");
    }

    // อีเมลล์เป็นค่าว่าง
    public static VerifyException emailNull() {
        return new VerifyException("register.email.null");
    }

    //CREATE
    public static VerifyException createEmailNull() {
        return new VerifyException("create.email.null");
    }

    public static VerifyException createPasswordNull() {
        return new VerifyException("create.password.null");
    }

    public static VerifyException createNameNull() {
        return new VerifyException("create.name.null");
    }

    public static VerifyException createUserNameNull() {
        return new VerifyException("create.userName.null");
    }

    public static VerifyException createPhoneNumberNull() {
        return new VerifyException("create.phoneNumber.null");
    }


    public static VerifyException createEmailDuplicated() {
        return new VerifyException("create.email.duplicated");
    }

    public static VerifyException createUserNameDuplicated() {
        return new VerifyException("create.userName.duplicated");
    }


    //LOGIN
    public static VerifyException loginFailUserNameNotFound() {
        return new VerifyException("login.fail");
    }

    public static VerifyException loginFailPasswordIncorrect() {
        return new VerifyException("login.fail");
    }


}
