package com.example.lovelypet.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

import static jakarta.persistence.FetchType.EAGER;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity(name = "pet_type")
public class PetType extends BaseEntity {

    @Column(nullable = false, length = 60,unique = true)
    private String name;

    //FK
    @OneToMany(mappedBy = "petTypeId", fetch = EAGER, orphanRemoval = true)
    private List<Pet> pet;

}
