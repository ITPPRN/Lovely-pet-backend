package com.example.lovelypet.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;
import java.util.List;

import static jakarta.persistence.FetchType.EAGER;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity(name = "hotel")
public class Hotel extends BaseEntity {

    @Column(nullable = false, length = 60)
    private String hotelName;

    @Column(nullable = false, length = 254)
    private String location;

    @Column(nullable = false, length = 20)
    private String hotelTel;

    @Column(nullable = false, length = 20)
    private float rating;

    @Column(nullable = false, length = 20)
    private String openState;

    @Column(nullable = false, length = 20)
    private String verify;

    @Column(nullable = false, length = 60, unique = true)
    private String hotelUsername;

    @Column(nullable = false, length = 60)
    private String password;

    @Column(nullable = false)
    private double latitude;

    @Column(nullable = false)
    private double longitude;

    @Column(nullable = false, length = 120, unique = true)
    private String email;

    @Column(nullable = true, length = 254)
    private String additionalNotes;

    @Column(nullable = true)
    private Date dateDeleteAccount;

    //FK

    //entity room
    @OneToMany(mappedBy = "hotelId", fetch = EAGER, orphanRemoval = true)
    private List<Room> rooms;

    //entity photo hotel
    @OneToMany(mappedBy = "hotelId", fetch = EAGER, orphanRemoval = true)
    private List<PhotoHotel> photoHotels;

    //entity AdditionalServices
    @OneToMany(mappedBy = "hotelId", fetch = EAGER, orphanRemoval = true)
    private List<AdditionalServices> additionalService;

    //entity booking
    @OneToMany(mappedBy = "hotelId", fetch = EAGER, orphanRemoval = true)
    private List<Booking> bookings;

    //entity service history
    @OneToMany(mappedBy = "hotelId", fetch = EAGER, orphanRemoval = true)
    private List<ServiceHistory> serviceHistories;

    //entity review
    @OneToMany(mappedBy = "hotelId", fetch = EAGER, orphanRemoval = true)
    private List<Review> reviews;

    private Date tokenExpire;
    private String token;
    private boolean activated;
}
