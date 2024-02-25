package com.example.lovelypet.model;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class BookingByClinicRequest {
    private String customerName;
    private int roomId;
    private String petName;
    private String start;
    private String end;
    private String paymentMethod;
    private int idBooking;
    private String additionService;
    private double totalPrice;



    private  String state;

}
