package com.vehicle.payrent.user.dto;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class BookingRequest {

    @NotNull
    private Integer vehicleId;

    @Min(1)
    private Integer noOfDays;
}
