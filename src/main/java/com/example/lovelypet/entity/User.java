package com.example.lovelypet.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import static jakarta.persistence.FetchType.EAGER;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity(name = "userprofile")
public class User extends BaseEntity implements Serializable {

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


    @Column(nullable = true)
    private String userPhoto;

    @Column(nullable = true)
    private Date dateDeleteAccount;

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

    private Date tokenExpire;
    private String token;
    private boolean activated;
}
