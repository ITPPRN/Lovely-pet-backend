package com.example.lovelypet.exception;

public class RoomException extends BaseException {
    public RoomException(String code) {
        super("room." + code);
    }

    //CREATE
    public static RoomException createRoomPriceNull() {
        return new RoomException("create.roomPrice.null");
    }

    public static RoomException createHotelIdNull() {
        return new RoomException("create.hotelId.null");
    }

    public static RoomException createRoomIdNull() {
        return new RoomException("create.roomId.null");
    }

    public static RoomException createTotalRoomNull() {
        return new RoomException("create.totalRoom.null");
    }

    //find
    public static RoomException notFound() {
        return new RoomException("room.not.found");
    }

    public static RoomException notFoundRoomType() {
        return new RoomException("room.type.not.found");
    }

}
