package com.example.lovelypet.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity(name = "pet")
public class Pet extends BaseEntity {

    @Column(nullable = false, length = 60)
    private String PetName;

    @Column(nullable = true, length = 254)
    private String PetPhoto;
}
