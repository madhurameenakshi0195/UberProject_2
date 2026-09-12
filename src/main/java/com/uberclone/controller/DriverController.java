package com.uberclone.controller;

//import com.uberclone.dto.DriverLocationDTO;
import com.uberclone.dto.DriverLocationDTO;
import com.uberclone.dto.DriverLoginDTO;
import com.uberclone.dto.DriverRegisterDTO;
import com.uberclone.entity.Driver;
import com.uberclone.repository.DriverRepository;
import com.uberclone.service.DriverService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/drivers")
@RequiredArgsConstructor
public class DriverController {

    private final DriverService driverService;
    private final DriverRepository driverRepository;



    @PostMapping("/register")
    public Driver registerDriver(
            @RequestBody DriverRegisterDTO dto) {

        return driverService.registerDriver(dto);


    }

    @PutMapping("/availability/{driverId}")
    public ResponseEntity<String> updateAvailability(

            @PathVariable Long driverId,

            @RequestParam Boolean available
    ) {

        driverService.updateAvailability(driverId, available);

        return ResponseEntity.ok("Driver availability updated");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody DriverLoginDTO dto) {

        Driver driver = driverService.login(dto);

        String token = driverService.generateToken(driver.getEmail());

        return ResponseEntity.ok(
                Map.of(
                        "token", token,
                        "driverId", driver.getId(),
                        "name", driver.getName(),
                        "email", driver.getEmail(),
                        "vehicleNumber", driver.getVehicleNumber(),
                        "vehicleModel", driver.getVehicleModel(),
                        "available", driver.getAvailable()
                )
        );
    }
    @GetMapping
    public List<Driver> getAllDrivers() {
        return driverRepository.findAll();
    }

    @GetMapping("/available")
    public List<Driver> getAvailableDrivers() {
        return driverRepository.findByAvailableTrue();
    }

    @PutMapping("/location/{driverId}")
    public ResponseEntity<String> updateLocation(

            @PathVariable Long driverId,

            @RequestBody DriverLocationDTO dto
    ) {

        driverService.updateLocation(
                driverId,
                dto.getLatitude(),
                dto.getLongitude()
        );

        return ResponseEntity.ok("Driver location updated");
    }
}
