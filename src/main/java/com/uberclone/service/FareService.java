package com.uberclone.service;

import org.springframework.stereotype.Service;

@Service
public class FareService {

    private static final double BASE_FARE = 50.0;
    private static final double PER_KM_RATE = 12.0;

    public double calculateFare(double distance) {
        return Math.round((BASE_FARE + distance * PER_KM_RATE) * 100.0) / 100.0;
    }
}