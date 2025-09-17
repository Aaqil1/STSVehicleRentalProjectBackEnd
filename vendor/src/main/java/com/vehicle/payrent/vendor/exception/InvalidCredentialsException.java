package com.vehicle.payrent.vendor.exception;

public class InvalidCredentialsException extends VehicleRentalException {

    public InvalidCredentialsException() {
        super("Invalid username or password");
    }
}
