package com.example.lovelypet.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity(name = "userprofile")
public class User extends BaseEntity {

    @Column(nullable = false,length = 60,unique = true)
    private String userName;

    @Column(nullable = false,length = 120)
    private String passWord;

    @Column(nullable = false,length = 120)
    private String name;

    @Column(nullable = false,length = 120,unique = true)
    private String email;

    @Column(nullable = false,length = 120)
    private String phoneNumber;
}
