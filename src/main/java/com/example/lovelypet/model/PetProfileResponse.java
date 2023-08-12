package com.example.lovelypet.model;

import lombok.Data;

@Data
public class PetProfileResponse {

    private int id;
    private String PetName;
    private String birthday;
    private String type;
}
