package com.example.lovelypet.model;

import lombok.Data;

@Data
public class AddPetRequest {
   private int userId;
   private String name;
   private String birthday;
   private String PetPhoto;
   private String type;
}
