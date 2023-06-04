package com.example.lovelypet.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;

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

    @Column(nullable = false, length = 60,unique = true)
    private String hotelUsername;

    @Column(nullable = false, length = 60)
    private String password;

    @Column(nullable = false, length = 120,unique = true)
    private String email;

    //FK

    //entity room
    @OneToMany(mappedBy = "hotelId", fetch = EAGER, orphanRemoval = true)
    private List<Room> rooms;

    //entity photo hotel
    @OneToMany(mappedBy = "hotelId", fetch = EAGER, orphanRemoval = true)
    private List<PhotoHotel> photoHotels;

    //entity booking
    @OneToMany(mappedBy = "hotelId", fetch = EAGER, orphanRemoval = true)
    private List<Booking> bookings;

    //entity service history
    @OneToMany(mappedBy = "hotelId", fetch = EAGER, orphanRemoval = true)
    private List<ServiceHistory> serviceHistories;

    //entity review
    @OneToMany(mappedBy = "hotelId", fetch = EAGER, orphanRemoval = true)
    private List<Review> reviews;
}
