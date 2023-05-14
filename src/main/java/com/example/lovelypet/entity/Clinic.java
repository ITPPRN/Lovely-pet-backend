package com.example.lovelypet.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity(name = "clinicprofile")
public class Clinic extends BaseEntity {

    @Column(nullable = false,length = 60,unique = true)
    private String userNameC;

    @Column(nullable = false,length = 120)
    private String passWordC;

    @Column(nullable = false,length = 120)
    private String nameC;

    @Column(nullable = false,length = 120,unique = true)
    private String emailC;

    @Column(nullable = false,length = 120)
    private String telC;

    @Column(nullable = false,length = 255)
    private String addressC;

    @Column(nullable = false,length = 120)
    private String statusC;

    @Column(nullable = false,length = 255)
    private String photoC;

    @Column(nullable = false,length = 255,unique = true)
    private String licenseC;

    @Column(nullable = false,length = 255)
    private String detailsC;

    @Column(nullable = false,length = 2)
    private float ratingC;

    @Column(nullable = false,length = 120)
    private String statusVerify;
}
