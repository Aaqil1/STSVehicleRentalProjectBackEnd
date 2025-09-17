package com.vehicle.payrent.user.exception;

public class InvalidCredentialsException extends VehicleRentalException {

    public InvalidCredentialsException() {
        super("Invalid username or password");
    }
}
