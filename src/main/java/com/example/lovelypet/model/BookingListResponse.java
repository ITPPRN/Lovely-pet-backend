package com.example.lovelypet.model;

import com.example.lovelypet.entity.Pet;
import com.example.lovelypet.entity.User;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
public class BookingListResponse {
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
    private double price;

}
