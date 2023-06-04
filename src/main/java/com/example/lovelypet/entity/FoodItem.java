package com.example.lovelypet.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity(name = "food_item")
public class FoodItem extends BaseEntity {

    @Column(nullable = false, length = 60)
    private String foodItemName;

    @Column(nullable = false, length = 10)
    private float foodItemPrice;

    @Column(nullable = true, length = 254)
    private String foodItemPhoto;

    // Fk

    //entity type food
    @ManyToOne
    @JoinColumn(name = "typeFood", nullable = false)
    private TypeFood typeFood;
}
