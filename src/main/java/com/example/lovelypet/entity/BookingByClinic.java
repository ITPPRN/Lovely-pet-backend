package com.example.lovelypet.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity(name = "booking_by_clinic")
public class BookingByClinic extends BaseEntity {

    @Column(nullable = false, length = 60)
    private Date bookingStartDate;//

    @Column(nullable = false, length = 60)
    private Date bookingEndDate;//

    @Column(nullable = false, length = 60)
    private LocalDateTime date;//

    @Column(nullable = false, length = 60)
    private String paymentMethod;//

    @Column(nullable = false, length = 60)
    private String statusBooking;//

    @Column(nullable = false, length = 60)
    private double totalPrice;//

    @Column(nullable = true, length = 60)
    private String nameCustomer;//

    @Column(nullable = true, length = 60)
    private String namePet;//

    private int roomId;//

    private String additionalServiceId;//

    private int hotelId;//


}
