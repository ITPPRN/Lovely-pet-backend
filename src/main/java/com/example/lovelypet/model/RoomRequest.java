package com.example.lovelypet.model;

import lombok.Data;

@Data
public class RoomRequest {
    private int total;
    private int hotelId;
    private String type;
    private float price;
    private String Details;
    private int id;
    private String status;

    private int idPhoto;
    private String namePhoto;

}
