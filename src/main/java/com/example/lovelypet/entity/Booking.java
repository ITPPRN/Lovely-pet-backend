package com.example.lovelypet.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity(name = "booking")
public class Booking extends BaseEntity {

    @Column(nullable = false, length = 60)
    private Date bookingStartDate;

    @Column(nullable = false, length = 60)
    private Date bookingEndDate;

    @Column(nullable = false, length = 60)
    private LocalDateTime date;

    @Column(nullable = false, length = 60)
    private String paymentMethod;

    @Column(nullable = true, length = 254)
    private String payment;

    @Column(nullable = false, length = 60)
    private String state;

    @Column(nullable = false, length = 60)
    private double totalPrice;

    @Column(nullable = true, length = 60)
    private String nameCustomer;

    @Column(nullable = true, length = 60)
    private String namePet;

    @Column(nullable = true, length = 60)
    private boolean bookingByClinic;

    //FK

    // entity room
    @ManyToOne
    @JoinColumn(name = "roomId", referencedColumnName = "id")
    private Room roomId;

    // entity additionalService
    @ManyToOne
    @JoinColumn(name = "additionalServiceId", nullable = true)
    private AdditionalServices additionalServiceId;

    // entity pet
    @ManyToOne
    @JoinColumn(name = "petId", referencedColumnName = "id", nullable = true)
    private Pet petId;

    // entity hotel
    @ManyToOne
    @JoinColumn(name = "hotelId", nullable = false)
    private Hotel hotelId;

    // entity user
    @ManyToOne
    @JoinColumn(name = "userId", nullable = true)
    private User userId;

    // entity service history
    @OneToOne(mappedBy = "bookingId", orphanRemoval = true)
    private ServiceHistory serviceHistory;


}
