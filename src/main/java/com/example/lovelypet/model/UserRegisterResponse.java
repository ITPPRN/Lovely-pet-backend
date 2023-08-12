package com.example.lovelypet.model;

import lombok.Data;

@Data
public class UserRegisterResponse {
    private int id;
    private String userName;
    private String name;
    private String email;
    private String phoneNumber;
}
