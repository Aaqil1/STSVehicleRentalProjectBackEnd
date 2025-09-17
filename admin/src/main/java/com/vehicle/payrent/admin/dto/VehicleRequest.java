package com.vehicle.payrent.admin.dto;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Data
public class VehicleRequest {

    @NotBlank
    private String vehicleName;

    private boolean booked;

    @Min(0)
    private Integer rentPerDay;
}
