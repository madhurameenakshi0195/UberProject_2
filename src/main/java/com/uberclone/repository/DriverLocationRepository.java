package com.uberclone.repository;

import com.uberclone.entity.Driver;
import com.uberclone.entity.DriverLocation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DriverLocationRepository
        extends JpaRepository<DriverLocation, Long> {

    Optional<DriverLocation> findByDriver(Driver driver);
}