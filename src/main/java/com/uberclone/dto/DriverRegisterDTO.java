package com.uberclone.dto;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class DriverRegisterDTO {

    private String name;


    @Column(unique = true)
    private String email;

    private String password;

    private String vehicleNumber;

    private String vehicleModel;


}