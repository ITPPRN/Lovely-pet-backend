package com.example.lovelypet.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity(name = "review")
public class Review extends BaseEntity {

    @Column(nullable = false, length = 2)
    private float rating;

    @Column(nullable = false, length = 254)
    private String comment;


    //FK
    // entity hotel
    @ManyToOne
    @JoinColumn(name = "hotelId", nullable = false)
    private Hotel hotelId;

    // entity user
    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private User userId;
}
