package com.example.lovelypet.model;
import com.example.lovelypet.entity.Hotel;
import com.example.lovelypet.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
public class ReviewResponse {

    private int id;
    private float rating;
    private String comment;
    private int hotelId;
    private int userId;
}
