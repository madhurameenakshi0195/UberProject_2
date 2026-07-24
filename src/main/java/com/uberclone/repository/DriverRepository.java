package com.uberclone.repository;

import com.uberclone.entity.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface DriverRepository
        extends JpaRepository<Driver, Long> {

    List<Driver> findByAvailableTrue();
    
    Optional<Driver> findByEmail(String email);
}