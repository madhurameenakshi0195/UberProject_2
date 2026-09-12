package com.uberclone.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
    @JsonManagedReference
    private DriverLocation driverLocation;

    @OneToMany(mappedBy = "driver")
    @JsonIgnore
    private List<Ride> rides;

    private Double rating;

    private Integer completedRides;

    private Double acceptanceRate;
}