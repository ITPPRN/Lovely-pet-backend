package com.example.lovelypet.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BookingByClinicListResponse {
    String addSer;
    private int id;
    private String bookingStartDate;
    private String bookingEndDate;
    private LocalDateTime date;
    private String paymentMethod;
    private String state;
    private int roomNumber;
    private String pet;
    private String user;
    private double price;

}
