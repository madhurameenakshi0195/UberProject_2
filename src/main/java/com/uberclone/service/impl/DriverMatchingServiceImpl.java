package com.uberclone.service.impl;

import com.uberclone.entity.Driver;
import com.uberclone.repository.DriverRepository;
import com.uberclone.service.DriverMatchingService;
import com.uberclone.util.DistanceUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DriverMatchingServiceImpl extends DriverMatchingService {

    private final DriverRepository driverRepository;

    public DriverMatchingServiceImpl(DriverRepository driverRepository, DriverRepository driverRepository1) {
        super(driverRepository);
        this.driverRepository = driverRepository1;
    }

    @Override
    public Driver findNearestDriver(
            Double pickupLat,
            Double pickupLng
    ) {

        List<Driver> drivers =
                driverRepository.findByAvailableTrue();

        if (drivers.isEmpty()) {
            return null;
        }

        Driver nearestDriver = null;
        double shortestDistance = Double.MAX_VALUE;

        for (Driver driver : drivers) {

            if (driver.getCurrentLat() == null ||
                    driver.getCurrentLng() == null) {
                continue;
            }

            double distance =
                    DistanceUtil.calculateDistance(
                            pickupLat,
                            pickupLng,
                            driver.getCurrentLat(),
                            driver.getCurrentLng()
                    );

            if (distance < shortestDistance) {

                shortestDistance = distance;
                nearestDriver = driver;

            }
        }

        return nearestDriver;
    }
}