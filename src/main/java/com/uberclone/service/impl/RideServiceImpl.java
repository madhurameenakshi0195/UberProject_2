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
import com.uberclone.service.FarePredictionService;
import com.uberclone.service.FareService;
import com.uberclone.service.RideService;
import com.uberclone.util.DistanceUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor

public class RideServiceImpl implements RideService {

    private final RideRepository rideRepository;
    private final UserRepository userRepository;
    private final DriverMatchingService driverMatchingService;
    private final DriverRepository driverRepository;
    private final FarePredictionService farePredictionService;

    @Override
    public Ride requestRide(RideRequestDTO dto, String email) {


        double distance = DistanceUtil.calculateDistance(
                dto.getPickupLat(),
                dto.getPickupLng(),
                dto.getDropLat(),
                dto.getDropLng()
        );




        // Simple estimate: average speed ≈ 30 km/h
        double averageSpeed = 30.0; // km/h

        double duration = (distance / averageSpeed) * 60;

        double fare = farePredictionService.predictFare(distance, duration);

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
                .fare(Math.round(fare * 100.0) / 100.0)
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

    @Override
    public Ride getRide(Long rideId) {

        return rideRepository.findById(rideId)
                .orElseThrow(() ->
                        new RuntimeException("Ride not found"));
    }

    @Override
    public Ride getCurrentRide(Long driverId) {

        Driver driver = driverRepository.findById(driverId)
                .orElseThrow(() ->
                        new RuntimeException("Driver not found"));

        List<RideStatus> activeStatuses = List.of(
                RideStatus.DRIVER_ASSIGNED,
                RideStatus.ACCEPTED,
                RideStatus.STARTED
        );

        for (RideStatus status : activeStatuses) {

            List<Ride> rides =
                    rideRepository.findByDriverAndStatus(
                            driver,
                            status
                    );

            if (!rides.isEmpty()) {
                return rides.get(0);
            }
        }

        return null;
    }


}