package com.vehicle.payrent.user.dto;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class PaymentRequest {

    @NotNull
    private Integer bookingId;

    @Min(0)
    private Integer totalAmount;
}
