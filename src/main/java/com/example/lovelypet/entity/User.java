package com.example.lovelypet.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.util.Lazy;

import java.util.List;

import static jakarta.persistence.FetchType.EAGER;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity(name = "userprofile")
public class User extends BaseEntity {

    @Column(nullable = false, length = 60, unique = true)
    private String userName;

    @Column(nullable = false, length = 120)
    private String passWord;

    @Column(nullable = false, length = 120)
    private String name;

    @Column(nullable = false, length = 120, unique = true)
    private String email;

    @Column(nullable = false, length = 10)
    private String phoneNumber;


    @Column(nullable = true, length = 254)
    private String userPhoto;

    // Fk

    // entity pet
    @OneToMany(mappedBy = "userId", fetch = EAGER, orphanRemoval = true)
    private List<Pet> pet;

    //entity booking
    @OneToMany(mappedBy = "userId", fetch = EAGER, orphanRemoval = true)
    private List<Booking> bookings;

    //entity service history
    @OneToMany(mappedBy = "userId", fetch = EAGER, orphanRemoval = true)
    private List<ServiceHistory> serviceHistories;

    //entity review
    @OneToMany(mappedBy = "userId", fetch = EAGER, orphanRemoval = true)
    private List<Review> reviews;
}
