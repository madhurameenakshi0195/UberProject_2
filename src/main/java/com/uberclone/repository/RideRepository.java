package com.uberclone.repository;

import com.uberclone.entity.Driver;
import com.uberclone.entity.Ride;
import com.uberclone.entity.RideStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RideRepository extends JpaRepository<Ride, Long> {

    List<Ride> findByDriverAndStatus(
            Driver driver,
            RideStatus status
    );

}