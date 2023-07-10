package com.example.lovelypet.exception;

public class UserException extends BaseException {
    public UserException(String code) {
        super("user." + code);
    }

    public static UserException requestNull() {
        return new UserException("register.request.null");
    }

    public static UserException notFound() {
        return new UserException("user.not.found");
    }

    public static UserException unauthorized() {
        return new UserException("unauthorized");
    }

    // อีเมลล์เป็นค่าว่าง
    public static UserException emailNull() {
        return new UserException("register.email.null");
    }

    //CREATE
    public static UserException createEmailNull() {
        return new UserException("create.email.null");
    }
    public static UserException createPasswordNull() {
        return new UserException("create.password.null");
    }
    public static UserException createNameNull() {
        return new UserException("create.name.null");
    }
    public static UserException createUserNameNull() {
        return new UserException("create.userName.null");
    }
    public static UserException createPhoneNumberNull() {
        return new UserException("create.phoneNumber.null");
    }


    public static UserException createEmailDuplicated() {
        return new UserException("create.email.duplicated");
    }
    public static UserException createUserNameDuplicated() {
        return new UserException("create.userName.duplicated");
    }


    //LOGIN
    public static UserException loginFailUserNameNotFound() {
        return new UserException("login.fail");
    }

    public static UserException loginFailPasswordIncorrect() {
        return new UserException("login.fail");
    }

    public static UserException loginFailUserUnactivated() {
        return new UserException("login.fail.unactivated");
    }

    //reset password
    public static UserException passwordIncorrect() {
        return new UserException("password.incorrect");
    }

    //ACTIVATE
    public static UserException activateNoToken() {
        return new UserException("activate.no.token");
    }

    public static UserException activateAlready() {
        return new UserException("activate.already");
    }
    public static UserException activateFail() {
        return new UserException("activate.fail");
    }
    public static UserException activateTokenExpire() {
        return new UserException("activate.token.expire");
    }

    //resend activation
    public static UserException resendActivationEmailNoEmail() {
        return new UserException("resend.activation.no.email");
    }

    public static UserException resendActivationEmailNoEmailEmailNotFound() {
        return new UserException("resend.activation.fail");
    }
}
