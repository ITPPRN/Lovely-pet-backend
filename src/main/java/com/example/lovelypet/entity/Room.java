package com.example.lovelypet.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

import static jakarta.persistence.FetchType.EAGER;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity(name = "room")
public class  Room extends BaseEntity {

    @Column(nullable = false, length = 3)
    private int roomNumber;

    @Column(nullable = true, length = 254)
    private String roomDetails;

    @Column(nullable = false, length = 10)
    private float roomPrice;

    @Column(nullable = false, length = 10)
    private String status;

    // Fk

    //entity hotel
    @ManyToOne
    @JoinColumn(name = "hotelId", nullable = false)
    private Hotel hotelId;

    //entity photo room
    @OneToMany(mappedBy = "roomId", fetch = EAGER, orphanRemoval = true)
    private List<PhotoRoom> photoRooms;

    @ManyToOne()
    private Booking booking;
}
