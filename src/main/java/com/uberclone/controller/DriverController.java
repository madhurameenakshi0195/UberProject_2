package com.uberclone.controller;

//import com.uberclone.dto.DriverLocationDTO;
import com.uberclone.dto.DriverLocationDTO;
import com.uberclone.dto.DriverRegisterDTO;
import com.uberclone.entity.Driver;
import com.uberclone.repository.DriverRepository;
import com.uberclone.service.DriverService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping
    public List<Driver> getAllDrivers() {
        return driverRepository.findAll();
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
