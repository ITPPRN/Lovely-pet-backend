package com.example.lovelypet.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity(name = "hotel_servicePetType")
public class HotelService extends BaseEntity {

    @Column(nullable = false, length = 60)
    private String serviceName;
}
