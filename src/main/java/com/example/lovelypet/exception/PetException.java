package com.example.lovelypet.exception;

public class PetException extends BaseException {
    public PetException(String code) {
        super("room." + code);
    }

    //CREATE
    public static PetException createPetNameNull() {
        return new PetException("create.pet.name.null");
    }

    public static PetException createPetTypeNull() {
        return new PetException("create.pet.type.null");
    }

    public static PetException createBirthDayNull() {
        return new PetException("create.birth.day.null");
    }

    public static PetException createPetOwnerNull() {
        return new PetException("create.pet.owner.null");
    }

    //find
    public static PetException notFound() {
        return new PetException("pet.not.found");
    }

    //upload image
    public static PetException imageAlreadyExists() {
        return new PetException("image.already.exists");
    }

    public static PetException uploadIdNull() {
        return new PetException("upload.id.null");
    }

    //update
    public static PetException updateIdNull() {
        return new PetException("update.id.null");
    }
}
