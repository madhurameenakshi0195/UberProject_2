package com.uberclone.service;

import com.uberclone.entity.Driver;
import com.uberclone.repository.DriverRepository;
import com.uberclone.util.DistanceUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DriverMatchingService {

    private final DriverRepository driverRepository;

    public Driver findNearestDriver(
            Double pickupLat,
            Double pickupLng
    ) {

        List<Driver> drivers = driverRepository.findByAvailableTrue();

        Driver nearestDriver = null;
        double minDistance = Double.MAX_VALUE;

        for (Driver driver : drivers) {

            if (driver.getDriverLocation() == null) {
                continue;
            }

            double distance = DistanceUtil.calculateDistance(
                    pickupLat,
                    pickupLng,
                    driver.getDriverLocation().getLatitude(),
                    driver.getDriverLocation().getLongitude()
            );

            if (distance < minDistance) {
                minDistance = distance;
                nearestDriver = driver;
            }
        }

        return nearestDriver;
    }
}