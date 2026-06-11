package com.uberclone.repository;

import com.uberclone.entity.Ride;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RideRepository
        extends JpaRepository<Ride, Long> {
}