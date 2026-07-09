package com.uberclone.service.impl;

//import com.uberclone.dto.DriverLocationDTO;
import com.uberclone.dto.DriverRegisterDTO;
import com.uberclone.entity.Driver;
import com.uberclone.repository.DriverRepository;
import com.uberclone.service.DriverService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DriverServiceImpl implements DriverService {

    private final DriverRepository driverRepository;

    @Override
    public Driver registerDriver( DriverRegisterDTO dto) {

        Driver driver = Driver.builder()
                .name(dto.getName())
                .vehicleNumber(dto.getVehicleNumber())
                .vehicleModel(dto.getVehicleModel())
                .currentLat(dto.getCurrentLat())
                .currentLng(dto.getCurrentLng())
                .available(true)
                .build();

        return driverRepository.save(driver);
    }
    @Override
    public void updateLocation(
            Long driverId,
            Double latitude,
            Double longitude
    ) {

        Driver driver = driverRepository.findById(driverId)
                .orElseThrow(() ->
                        new RuntimeException("Driver not found"));

        driver.setCurrentLat(latitude);
        driver.setCurrentLng(longitude);

        driverRepository.save(driver);
    }

    }
