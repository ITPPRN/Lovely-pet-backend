package com.example.lovelypet.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity(name = "pet")
public class Pet extends BaseEntity implements Serializable {

    @Column(nullable = false, length = 60)
    private String PetName;

    @Column(nullable = false, length = 60)
    private Date birthday;

    @Column(nullable = true, length = 254)
    private String PetPhoto;

    // Fk

    // entity user
    @ManyToOne
    @JoinColumn(name = "userId",nullable = false)
    private User userId;

    //entity pet type
    @ManyToOne
    @JoinColumn(name = "petTypeId",nullable = false)
    private PetType petTypeId;


}
