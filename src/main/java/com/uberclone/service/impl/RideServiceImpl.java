package com.uberclone.service.impl;

import com.uberclone.dto.RideRequestDTO;
import com.uberclone.entity.Driver;
import com.uberclone.entity.Ride;
import com.uberclone.entity.RideStatus;
import com.uberclone.entity.User;
import com.uberclone.repository.DriverRepository;
import com.uberclone.repository.RideRepository;
import com.uberclone.repository.UserRepository;
import com.uberclone.service.DriverMatchingService;
import com.uberclone.service.RideService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor

public class RideServiceImpl implements RideService {

    private final RideRepository rideRepository;
    private final UserRepository userRepository;
    private final DriverMatchingService driverMatchingService;
    private final DriverRepository driverRepository;

    @Override
    public Ride requestRide(RideRequestDTO dto, String email) {

        User rider = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Driver driver = driverMatchingService.findNearestDriver(
                dto.getPickupLat(),
                dto.getPickupLng()

        );


        Ride ride = Ride.builder()
                .pickupLat(dto.getPickupLat())
                .pickupLng(dto.getPickupLng())
                .dropLat(dto.getDropLat())
                .dropLng(dto.getDropLng())
                .rider(rider)
                .driver(driver)
                .status(
                        driver != null
                                ? RideStatus.DRIVER_ASSIGNED
                                : RideStatus.REQUESTED
                )
                .build();

        if (driver != null) {
            driver.setAvailable(false);
            driverRepository.save(driver);

        }

        return rideRepository.save(ride);
    }

    @Override
    public Ride acceptRide(Long rideId, Long driverId) {

        Ride ride = rideRepository.findById(rideId)
                .orElseThrow(() ->
                        new RuntimeException("Ride not found"));

        if (ride.getDriver() == null) {
            throw new RuntimeException("No driver assigned");
        }

        if (!ride.getDriver().getId().equals(driverId)) {
            throw new RuntimeException("This ride belongs to another driver");
        }

        ride.setStatus(RideStatus.ACCEPTED);

        return rideRepository.save(ride);
    }

    @Override
    public Ride startRide(Long rideId) {

        Ride ride = rideRepository.findById(rideId)
                .orElseThrow(() ->
                        new RuntimeException("Ride not found"));

        if (ride.getStatus() != RideStatus.ACCEPTED) {
            throw new RuntimeException("Ride not accepted yet");
        }

        ride.setStatus(RideStatus.STARTED);

        return rideRepository.save(ride);
    }
    @Override
    public Ride completeRide(Long rideId) {

        Ride ride = rideRepository.findById(rideId)
                .orElseThrow(() ->
                        new RuntimeException("Ride not found"));

        if (ride.getStatus() != RideStatus.STARTED) {
            throw new RuntimeException("Ride not started");
        }

        ride.setStatus(RideStatus.COMPLETED);

        Driver driver = ride.getDriver();

        if (driver != null) {

            driver.setAvailable(true);

            driverRepository.save(driver);
        }

        return rideRepository.save(ride);
    }
}