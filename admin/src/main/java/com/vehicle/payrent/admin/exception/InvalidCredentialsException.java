package com.vehicle.payrent.admin.exception;

public class InvalidCredentialsException extends VehicleRentalException {

    public InvalidCredentialsException() {
        super("Invalid username or password");
    }
}
