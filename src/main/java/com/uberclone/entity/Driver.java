package com.uberclone.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Driver {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String vehicleNumber;

    private String vehicleModel;

    private Double currentLatitude;

    private Double currentLongitude;

    private Boolean available;
}