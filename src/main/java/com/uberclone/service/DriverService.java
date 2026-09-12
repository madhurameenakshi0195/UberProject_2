package com.uberclone.service;

import com.uberclone.dto.DriverLoginDTO;
import com.uberclone.dto.DriverRegisterDTO;
import com.uberclone.entity.Driver;
import com.uberclone.entity.DriverLocation;
import com.uberclone.repository.DriverRepository;
import com.uberclone.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class DriverService {

    @Autowired
    private DriverRepository driverRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;


    public Driver registerDriver(DriverRegisterDTO dto) {

        Driver driver = Driver.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .password(passwordEncoder.encode(dto.getPassword()))
                .vehicleNumber(dto.getVehicleNumber())
                .vehicleModel(dto.getVehicleModel())
                .available(false)
                .build();

        return driverRepository.save(driver);
    }


    public Driver login(DriverLoginDTO dto) {

        Driver driver = driverRepository.findByEmail(dto.getEmail())
                .orElseThrow(() ->
                        new RuntimeException("Driver not found"));

        if (!passwordEncoder.matches(
                dto.getPassword(),
                driver.getPassword()
        )) {
            throw new RuntimeException("Invalid credentials");
        }

        return driver;
    }


    public String generateToken(String email) {

        return jwtUtil.generateToken(email);
    }


    public void updateAvailability(
            Long driverId,
            Boolean available
    ) {

        Driver driver = driverRepository.findById(driverId)
                .orElseThrow(() ->
                        new RuntimeException("Driver not found"));

        driver.setAvailable(available);

        driverRepository.save(driver);
    }


    public void updateLocation(
            Long driverId,
            Double latitude,
            Double longitude
    ) {

        Driver driver = driverRepository.findById(driverId)
                .orElseThrow(() ->
                        new RuntimeException("Driver not found"));

        DriverLocation location =
                driver.getDriverLocation();

        if (location == null) {

            location = new DriverLocation();

            location.setDriver(driver);
        }

        location.setLatitude(latitude);
        location.setLongitude(longitude);

        driver.setDriverLocation(location);

        driverRepository.save(driver);
    }
}