package com.vehicle.payrent.user.exception;

public class ResourceNotFoundException extends VehicleRentalException {

    public ResourceNotFoundException(String resourceName, Object identifier) {
        super(String.format("%s with identifier '%s' was not found", resourceName, identifier));
    }
}
