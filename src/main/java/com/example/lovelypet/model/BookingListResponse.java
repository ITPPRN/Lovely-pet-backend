package com.example.lovelypet.model;

import com.example.lovelypet.entity.Pet;
import com.example.lovelypet.entity.User;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
public class BookingListResponse {
    private int id;
    private Date bookingStartDate;
    private Date bookingEndDate;
    private LocalDateTime date;
    private String paymentMethod;
    private String payment;
    private String state;
    private int roomNumber;
    private Pet petId;
    private User userId;

}
