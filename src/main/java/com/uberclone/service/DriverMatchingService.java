package com.uberclone.service;

import com.uberclone.entity.Driver;
import com.uberclone.repository.DriverRepository;
import com.uberclone.util.DistanceUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public abstract class DriverMatchingService {

    private final DriverRepository driverRepository;

    public Driver findNearestDriver(
            double pickupLat,
            double pickupLng) {

        List<Driver> drivers =
                driverRepository.findByAvailableTrue();

        Driver nearestDriver = null;
        double minDistance = Double.MAX_VALUE;

        for (Driver driver : drivers) {

            if (driver.getCurrentLat() == null ||
            driver.getCurrentLng() == null){
                continue;
            }

            double distance =
                    DistanceUtil.calculateDistance(
                            pickupLat,
                            pickupLng,
                            driver.getCurrentLat(),
                            driver.getCurrentLng()
                    );

            if (distance < minDistance) {
                minDistance = distance;
                nearestDriver = driver;
            }
        }

        return nearestDriver;
    }

    public abstract Driver findNearestDriver(
            Double pickupLat,
            Double pickupLng
    );
}