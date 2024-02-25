package com.example.lovelypet.model;

import lombok.Data;

@Data
public class HotelUpdateRequest {
    private String hotelName;
    private String location;
    private String hotelTel;
    private String additionalNotes;
    private String openState;
    private String oldPassword;
    private String newPassword;
}
