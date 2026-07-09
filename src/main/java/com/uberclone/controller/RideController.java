package com.uberclone.controller;

import com.uberclone.dto.RideRequestDTO;
import com.uberclone.entity.Ride;
import com.uberclone.service.RideService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rides")
@RequiredArgsConstructor
public class RideController {

    private final RideService rideService;

    @PostMapping("/request")
    public Ride requestRide(
            @Valid @RequestBody RideRequestDTO dto,
            Authentication authentication) {

        String email = authentication.getName();

        return rideService.requestRide(dto, email);
    }

    @PostMapping("/{rideId}/accept/{driverId}")
    public Ride acceptRide(
            @PathVariable Long rideId,
            @PathVariable Long driverId) {

        return rideService.acceptRide(
                rideId,
                driverId
        );
    }
    @PostMapping("/{rideId}/start")
    public Ride startRide(
            @PathVariable Long rideId) {

        return rideService.startRide(rideId);
    }
    @PostMapping("/{rideId}/complete")
    public Ride completeRide(
            @PathVariable Long rideId) {

        return rideService.completeRide(rideId);
    }
}