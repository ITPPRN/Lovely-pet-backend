package com.example.lovelypet.exception;

public class EmaillException extends BaseException {
    public EmaillException(String code) {
        super("email." + code);
    }

    public static  EmaillException templateNotFound(){
        return new EmaillException("template.not.found");
    }


}
