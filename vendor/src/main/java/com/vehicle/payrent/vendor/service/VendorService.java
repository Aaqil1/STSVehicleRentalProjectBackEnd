package com.vehicle.payrent.vendor.service;

import com.vehicle.payrent.vendor.dto.LoginRequest;
import com.vehicle.payrent.vendor.dto.VehicleRequest;
import com.vehicle.payrent.vendor.entity.BookingDetail;
import com.vehicle.payrent.vendor.entity.Vehicle;
import com.vehicle.payrent.vendor.entity.Vendor;
import com.vehicle.payrent.vendor.exception.InvalidCredentialsException;
import com.vehicle.payrent.vendor.exception.ResourceNotFoundException;
import com.vehicle.payrent.vendor.repository.BookingRepository;
import com.vehicle.payrent.vendor.repository.VehicleRepository;
import com.vehicle.payrent.vendor.repository.VendorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class VendorService {

    private final BookingRepository bookingRepository;
    private final VehicleRepository vehicleRepository;
    private final VendorRepository vendorRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public void authenticate(LoginRequest request) {
        Vendor vendor = vendorRepository.findByUsername(request.getUsername())
                .orElseThrow(InvalidCredentialsException::new);
        if (!passwordEncoder.matches(request.getPassword(), vendor.getPassword())) {
            throw new InvalidCredentialsException();
        }
    }

    @Transactional(readOnly = true)
    public List<BookingDetail> getAllBookingDetails() {
        return bookingRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Vehicle> getAllVehicleDetails() {
        return vehicleRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Vehicle getVehicle(Integer vehicleId) {
        return vehicleRepository.findById(vehicleId)
                .orElseThrow(() -> new ResourceNotFoundException("Vehicle", vehicleId));
    }

    public Vehicle updateVehicle(Integer vehicleId, VehicleRequest request) {
        Vehicle vehicle = getVehicle(vehicleId);
        vehicle.setBooked(request.isBooked());
        vehicle.setRentPerday(request.getRentPerDay());
        vehicle.setVehicleName(request.getVehicleName());
        return vehicleRepository.save(vehicle);
    }

    public void deleteVehicle(Integer vehicleId) {
        Vehicle vehicle = getVehicle(vehicleId);
        vehicleRepository.delete(vehicle);
    }
}
