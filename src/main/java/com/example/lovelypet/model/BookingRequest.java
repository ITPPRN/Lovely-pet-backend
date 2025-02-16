package com.example.lovelypet.model;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class BookingRequest {
    private int hotelId;
    private int roomId;
    private int petId;
    private String start;
    private String end;
    private String paymentMethod;
    private int idBooking;
    private int additionService;
    private double totalPrice;

    private int idPhoto;


}
