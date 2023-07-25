package com.example.lovelypet.exception;

public class PhotoRoomException extends BaseException {
    public PhotoRoomException(String code) {
        super("booking." + code);
    }

    //CREATE
    public static PhotoRoomException createPartFileNull() {
        return new PhotoRoomException("create.part.file.null");
    }

    public static PhotoRoomException createRoomIdNull() {
        return new PhotoRoomException("create.room.id.null");
    }

    //find
    public static PhotoRoomException notFound() {
        return new PhotoRoomException("photo.room.not.found");
    }

    //get image
    public static PhotoRoomException getImageFail() {
        return new PhotoRoomException("get.image.fail");
    }
}
