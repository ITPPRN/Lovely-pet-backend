package com.example.lovelypet.exception;

public class PetException extends BaseException {
    public PetException(String code) {
        super("room." + code);
    }

    //CREATE

    //find
    public static PetException notFound() {
        return new PetException("pet.not.found");
    }

}
