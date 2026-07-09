package com.uberclone.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

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

    private Double currentLat;

    private Double currentLng;

    private Boolean available;


    @OneToMany(mappedBy = "driver")


    private List<Ride> rides;
}