package com.uberclone.service;

import com.uberclone.dto.DriverRegisterDTO;
import com.uberclone.entity.Driver;

public interface DriverService {

    Driver registerDriver(DriverRegisterDTO dto);

    void updateLocation(
            Long driverId,
            Double latitude,
            Double longitude
    );
}