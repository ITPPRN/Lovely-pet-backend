package com.example.lovelypet.model;

import lombok.Data;

@Data
public class UpdatePetRequest {

    private int id;
    private String name;
    private String birthday;
    private String type;
}
