package com.example.lovelypet.model;

import lombok.Data;

@Data
public class ReviewRequest {

    private int idHotel;
    private float rating;
    private String comment;
    private int bookingId;

    private int id;
}
