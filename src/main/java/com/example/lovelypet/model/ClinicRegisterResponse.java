package com.example.lovelypet.model;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class ClinicRegisterResponse {

    private int idU;
    private String nameC;
    private String emailC;
    private String telC;
    private String addressC;
    private String statusC;
    private String photoC;
    private String licenseC;
    private String detailsC;
    private float ratingC;
    private String statusVerify;
}
