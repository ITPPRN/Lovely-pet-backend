package com.example.lovelypet.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity(name = "category_service")
public class Category extends BaseEntity {

    @Column(nullable = false, length = 60, unique = true)
    private String name;
}
