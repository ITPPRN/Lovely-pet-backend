package com.example.lovelypet.model;
import jakarta.persistence.Column;
import lombok.Data;

@Data
public class AdditionalServiceResponse {

    private int id;
    private String name;
    private float price;
}
