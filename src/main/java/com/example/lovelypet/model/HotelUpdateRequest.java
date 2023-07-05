package com.example.lovelypet.model;

import lombok.Data;

@Data
public class HotelUpdateRequest {
    private int id;
    private String hotelName;
    private String location;
    private String hotelTel;
    private String oldPassword;
    private String newPassword;
}
