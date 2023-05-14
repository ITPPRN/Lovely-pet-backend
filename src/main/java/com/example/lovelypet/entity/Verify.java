package com.example.lovelypet.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity(name = "verify_profile")
public class Verify extends BaseEntity {

    @Column(nullable = false,length = 60,unique = true)
    private String userName;

    @Column(nullable = false,length = 120)
    private String passWord;

    @Column(nullable = false,length = 120,unique = true)
    private String email;

}
