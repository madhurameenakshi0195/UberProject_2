package com.uberclone.dto;

import lombok.Data;

@Data
public class DriverRegisterDTO {

    private String name;

    private String vehicleNumber;

    private String vehicleModel;

    private Double currentLat;

    private Double currentLng;
}