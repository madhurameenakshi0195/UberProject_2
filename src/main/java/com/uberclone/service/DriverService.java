package com.uberclone.service;

import com.uberclone.dto.DriverLoginDTO;
import com.uberclone.dto.DriverRegisterDTO;
import com.uberclone.entity.Driver;
import com.uberclone.repository.DriverRepository;
import com.uberclone.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class DriverService {



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

    public void updateLocation(
            Long driverId,
            Double latitude,
            Double longitude
    ) {

    }


    @Autowired
    private DriverRepository driverRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;




    @Autowired
    public JwtUtil jwtUtil;

    public String login(DriverLoginDTO dto) {

        Driver driver = driverRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new RuntimeException("Driver not found"));

        if (!passwordEncoder.matches(dto.getPassword(), driver.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        return jwtUtil.generateToken(driver.getEmail());
    }
}