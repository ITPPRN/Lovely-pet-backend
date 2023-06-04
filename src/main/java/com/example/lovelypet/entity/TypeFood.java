package com.example.lovelypet.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

import static jakarta.persistence.FetchType.EAGER;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity(name = "type_food")
public class TypeFood extends BaseEntity {

    @Column(nullable = false, length = 60)
    private String name;

    //FK
    @OneToMany(mappedBy = "typeFood", fetch = EAGER, orphanRemoval = true)
    private List<FoodItem> foodItems;
}
