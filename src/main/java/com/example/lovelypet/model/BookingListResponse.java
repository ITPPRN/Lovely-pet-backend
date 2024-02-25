package com.example.lovelypet.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BookingListResponse {
    AdditionalServiceResponse addSer;
    private int id;
    private String bookingStartDate;
    private String bookingEndDate;
    private LocalDateTime date;
    private String paymentMethod;
    private String payment;
    private String state;
    private int roomNumber;
    private PetProfileResponse pet;
    private UseProfile user;
    private int hotelId;
    private String nameHotel;
    private double latitude;
    private double longitude;
    private String telHotel;
    private String email;
    private boolean feedback;
    private double price;

}
