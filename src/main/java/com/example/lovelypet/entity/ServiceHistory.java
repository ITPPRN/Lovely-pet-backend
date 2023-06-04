package com.example.lovelypet.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity(name = "service_history")
public class ServiceHistory extends BaseEntity {
    // entity hotel
    @ManyToOne
    @JoinColumn(name = "hotelId", nullable = false)
    private Hotel hotelId;

    // entity user
    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private User userId;

    // entity booking
    @OneToOne
    @JoinColumn(name = "bookingId", nullable = false)
    private Booking bookingId;
}
