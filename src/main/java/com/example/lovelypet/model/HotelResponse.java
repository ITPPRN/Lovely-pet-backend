package com.example.lovelypet.model;

import lombok.Data;

@Data
public class HotelResponse {
    private int id;
    private String hotelName;
    private String location;
    private String hotelTel;
    private float rating;
    private String openState;
    private String verify;
    private String email;
    private String additionalNotes;

}
