package com.vehicle.payrent.user.exception;

public class PaymentVerificationException extends VehicleRentalException {

    public PaymentVerificationException() {
        super("Payment amount does not match booking total");
    }
}
