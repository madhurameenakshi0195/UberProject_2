package com.uberclone.service.impl;

//import com.uberclone.dto.DriverLocationDTO;
import com.uberclone.dto.DriverLoginDTO;
import com.uberclone.dto.DriverRegisterDTO;
import com.uberclone.entity.Driver;
import com.uberclone.entity.DriverLocation;
import com.uberclone.repository.DriverLocationRepository;
import com.uberclone.repository.DriverRepository;
import com.uberclone.security.JwtUtil;
import com.uberclone.service.DriverService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor


public class DriverServiceImpl extends DriverService {

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Autowired
    private DriverLocationRepository driverLocationRepository;

    private final DriverRepository driverRepository;
    @Override
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

    public String loginDriver(DriverLoginDTO dto){

        Driver driver = driverRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new RuntimeException("Driver not found"));

        if(!passwordEncoder.matches(dto.getPassword(), driver.getPassword())){
            throw new RuntimeException("Invalid password");
        }


        return jwtUtil.generateToken(driver.getEmail());
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

        DriverLocation driverLocation =
                driverLocationRepository
                        .findByDriver(driver)
                        .orElse(new DriverLocation());

        driverLocation.setDriver(driver);
        driverLocation.setLatitude(latitude);
        driverLocation.setLongitude(longitude);

        driverLocationRepository.save(driverLocation);

        driver.setAvailable(true);

        driverRepository.save(driver);
    }

    }
