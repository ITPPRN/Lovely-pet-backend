package com.example.lovelypet.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

import static jakarta.persistence.FetchType.EAGER;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity(name = "additional_service")
public class AdditionalServices extends BaseEntity {

    @Column(nullable = false, length = 254)
    private String name;

    @Column(nullable = false, length = 254)
    private float price;

    //FK
    //entity hotel
    @ManyToOne
    @JoinColumn(name = "hotelId", nullable = false)
    private Hotel hotelId;

    //FK booking
    @OneToMany(mappedBy = "additionalServiceId", fetch = EAGER, orphanRemoval = true)
    private List<Booking> bookings;
}
