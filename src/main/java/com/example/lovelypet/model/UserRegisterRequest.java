package com.example.lovelypet.model;

import lombok.Data;

@Data
public class UserRegisterRequest {
    private String userName;
    private String passWord;
    private String name;
    private String email;
    private String phoneNumber;
}
