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

    @Column(unique = true)
    private String email;

    private String password;

    private String name;

    private String vehicleNumber;

    private String vehicleModel;

    private Boolean available;

    @OneToOne(mappedBy = "driver",
            cascade = CascadeType.ALL)
    private DriverLocation driverLocation;


    @OneToMany(mappedBy = "driver")


    private List<Ride> rides;

    private Double rating;

    private Integer completedRides;

    private Double acceptanceRate;
}