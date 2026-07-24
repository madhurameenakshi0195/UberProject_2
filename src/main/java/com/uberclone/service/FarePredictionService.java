package com.uberclone.service;

import com.uberclone.dto.FareRequestDTO;
import com.uberclone.dto.FareResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class FarePredictionService {

    private final RestTemplate restTemplate;

    @Value("${python.api.url}")
    private String pythonApiUrl;

    public double predictFare(double distance, double duration) {

        FareRequestDTO request =
                new FareRequestDTO(distance, duration);

        FareResponseDTO response = restTemplate.postForObject(
                pythonApiUrl + "/predict-fare",
                request,
                FareResponseDTO.class
        );

        if (response == null) {
            throw new RuntimeException("Failed to get fare prediction");
        }

        return response.getFare();
    }
}