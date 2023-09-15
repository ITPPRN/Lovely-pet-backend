package com.example.lovelypet.model;

import lombok.Data;

@Data
public class PetProfileResponse {

    private int id;
    private String petName;
    private String birthday;
    private int petTypeId;
    private int userOwner;
    private String photoPath;


}
