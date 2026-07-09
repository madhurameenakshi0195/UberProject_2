package com.uberclone.service;

import com.uberclone.dto.RideRequestDTO;
import com.uberclone.entity.Ride;

public interface RideService {

    Ride requestRide(RideRequestDTO dto, String email);
    
    Ride acceptRide(Long rideId, Long driverId);

    Ride startRide(Long rideId);

    Ride completeRide(Long rideId);
}