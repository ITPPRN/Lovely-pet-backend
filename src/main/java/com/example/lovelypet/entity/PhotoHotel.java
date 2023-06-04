package com.example.lovelypet.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity(name = "photo_hotel")
public class PhotoHotel extends BaseEntity {

    @Column(nullable = true, length = 254)
    private String photoHotelFile;

    //FK
    //entity hotel
    @ManyToOne
    @JoinColumn(name = "hotelId", nullable = false)
    private Hotel hotelId;
}
