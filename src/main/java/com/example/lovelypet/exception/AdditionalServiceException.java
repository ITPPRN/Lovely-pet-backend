package com.example.lovelypet.exception;

public class AdditionalServiceException extends BaseException {
    public AdditionalServiceException(String code) {
        super("additional_service." + code);
    }

    //CREATE
    public static AdditionalServiceException createNameNull() {
        return new AdditionalServiceException("create.name.null");
    }

    public static AdditionalServiceException createPriceNull() {
        return new AdditionalServiceException("create.price.null");
    }

    //update

    public static AdditionalServiceException updateIdNull() {
        return new AdditionalServiceException("update.id.null");
    }
    public static AdditionalServiceException updateRequestNull() {
        return new AdditionalServiceException("update.request.null");
    }

    //delete

    public static AdditionalServiceException deleteIdNull() {
        return new AdditionalServiceException("delete.id.null");
    }

    public static AdditionalServiceException deleteFail() {
        return new AdditionalServiceException("delete.fail");
    }

    //find
    public static AdditionalServiceException notFound() {
        return new AdditionalServiceException("service.not.found");
    }


}
