package com.example.lovelypet.model;

import lombok.Data;

@Data
public class PetProfileResponse {

    private int id;
    private String petName;
    private String birthday;
    private String petTypeId;
    private int userOwner;
    private String photoPath;


}
