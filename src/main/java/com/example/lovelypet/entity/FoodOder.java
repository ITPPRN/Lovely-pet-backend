package com.example.lovelypet.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity(name = "food_oder")
public class FoodOder extends BaseEntity {

    @Column(nullable = false, length = 10)
    private float totalPrice;

    @Column(nullable = false, length = 254)
    private String paymentReceipt;

    @Column(nullable = false, length = 10)
    private String foodOderStaten;
}
