package com.uberclone.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RideRequestDTO {

        @NotNull
        private Double pickupLat;

        @NotNull
        private Double pickupLng;

        @NotNull
        private Double dropLat;

        @NotNull
        private Double dropLng;





}

