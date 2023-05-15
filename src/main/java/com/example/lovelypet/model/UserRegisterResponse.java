package com.example.lovelypet.model;

import lombok.Data;

@Data
public class UserRegisterResponse {
    private int idU;
    private String name;
    private String email;
    private String phoneNumber;
}
