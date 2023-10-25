package com.example.lovelypet.model;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class HotelRegisterRequest {
    private String hotelName;
    private String location;
    private String hotelTel;
    private String hotelUsername;
    private String password;
    private String email;
    private String additionalNotes;
    private double latitude;
    private double longitude;
}
