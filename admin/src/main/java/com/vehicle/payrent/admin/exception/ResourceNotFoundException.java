package com.vehicle.payrent.admin.exception;

public class ResourceNotFoundException extends VehicleRentalException {

    public ResourceNotFoundException(String resourceName, Object identifier) {
        super(String.format("%s with identifier '%s' was not found", resourceName, identifier));
    }
}
