package com.example.lovelypet.model;

import lombok.Data;

@Data
public class UserUpdateRequest {

    private String userName;
    private String oldPassword;
    private String newPassWord;
    private String name;
    private String email;
    private String phoneNumber;

}
