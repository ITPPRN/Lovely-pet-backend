package com.example.lovelypet.model;

import lombok.Data;

@Data
public class AdditionalServiceRequest {

    private String name;
    private float price;

    //case update
    private int id;
}
