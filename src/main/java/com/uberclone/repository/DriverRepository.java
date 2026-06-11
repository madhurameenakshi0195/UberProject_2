package com.uberclone.repository;

import com.uberclone.entity.Driver;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DriverRepository
        extends JpaRepository<Driver, Long> {
}