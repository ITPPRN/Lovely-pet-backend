package com.example.lovelypet.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity(name = "booking")
public class Booking extends BaseEntity {

    @Column(nullable = false, length = 60)
    private LocalDate bookingStartDate;

    @Column(nullable = false, length = 60)
    private LocalDate bookingEndDate;

    @Column(nullable = false, length = 60)
    private LocalDate date;

    //FK

    // entity room
    // อาจมีข้อผิดพลาดเพราะความสัมพัธ์ไม่แน่ชัด
    @OneToOne
    @JoinColumn(name = "roomId", referencedColumnName = "id")
    private Room roomId;

    // entity pet
    // อาจมีข้อผิดพลาดเพราะความสัมพัธ์ไม่แน่ชัด
    @OneToOne
    @JoinColumn(name = "petId", referencedColumnName = "id")
    private Pet petId;

    // entity hotel
    @ManyToOne
    @JoinColumn(name = "hotelId", nullable = false)
    private Hotel hotelId;

    // entity user
    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private User userId;

    // entity service history
    @OneToOne(mappedBy = "bookingId" , orphanRemoval = true)
    private ServiceHistory serviceHistory;




}
