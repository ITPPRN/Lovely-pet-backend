package com.example.lovelypet.model;

import lombok.Data;

@Data
public class HotelRegisterRequest {
    private String hotelName;
    private String location;
    private String hotelTel;
    private String hotelUsername;
    private String password;
    private String email;
}
