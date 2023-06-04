package com.example.lovelypet.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity(name = "pet_type")
public class PetType extends BaseEntity {

    @Column(nullable = false, length = 60)
    private String name;
}
