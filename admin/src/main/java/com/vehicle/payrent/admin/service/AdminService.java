package com.vehicle.payrent.admin.service;

import com.vehicle.payrent.admin.dto.LoginRequest;
import com.vehicle.payrent.admin.dto.VehicleRequest;
import com.vehicle.payrent.admin.dto.VendorRequest;
import com.vehicle.payrent.admin.entity.*;
import com.vehicle.payrent.admin.exception.DuplicateResourceException;
import com.vehicle.payrent.admin.exception.InvalidCredentialsException;
import com.vehicle.payrent.admin.exception.ResourceNotFoundException;
import com.vehicle.payrent.admin.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AdminService {

    private final AdminRepository adminRepository;
    private final VehicleRepository vehicleRepository;
    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final VendorRepository vendorRepository;
    private final FeedbackRepository feedbackRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public void authenticate(LoginRequest request) {
        Admin admin = adminRepository.findByAdminId(request.getUsername())
                .orElseThrow(() -> new InvalidCredentialsException());

        if (!passwordEncoder.matches(request.getPassword(), admin.getPassword())) {
            throw new InvalidCredentialsException();
        }
    }

    public Vehicle createVehicle(VehicleRequest request) {
        Vehicle vehicle = new Vehicle();
        vehicle.setVehicleName(request.getVehicleName());
        vehicle.setBooked(request.isBooked());
        vehicle.setRentPerday(request.getRentPerDay());
        return vehicleRepository.save(vehicle);
    }

    public Vehicle updateVehicle(Integer vehicleId, VehicleRequest request) {
        Vehicle vehicle = vehicleRepository.findById(vehicleId)
                .orElseThrow(() -> new ResourceNotFoundException("Vehicle", vehicleId));
        vehicle.setVehicleName(request.getVehicleName());
        vehicle.setBooked(request.isBooked());
        vehicle.setRentPerday(request.getRentPerDay());
        return vehicleRepository.save(vehicle);
    }

    public void deleteVehicle(Integer vehicleId) {
        Vehicle vehicle = vehicleRepository.findById(vehicleId)
                .orElseThrow(() -> new ResourceNotFoundException("Vehicle", vehicleId));
        vehicleRepository.delete(vehicle);
    }

    @Transactional(readOnly = true)
    public List<Vehicle> getAllVehicles() {
        return vehicleRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<BookingDetail> getAllBookingDetails() {
        return bookingRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Vendor createVendor(VendorRequest request) {
        vendorRepository.findByUsername(request.getUsername()).ifPresent(vendor -> {
            throw new DuplicateResourceException("Vendor username already exists");
        });
        Vendor vendor = new Vendor();
        vendor.setVendorName(request.getVendorName());
        vendor.setUsername(request.getUsername());
        vendor.setAddress(request.getAddress());
        vendor.setPhone(request.getPhone());
        vendor.setPassword(passwordEncoder.encode(request.getPassword()));
        Vendor saved = vendorRepository.save(vendor);
        saved.setPassword(null);
        return saved;
    }

    public Vendor updateVendor(Integer vendorId, VendorRequest request) {
        Vendor vendor = vendorRepository.findById(vendorId)
                .orElseThrow(() -> new ResourceNotFoundException("Vendor", vendorId));

        vendorRepository.findByUsername(request.getUsername())
                .filter(existing -> !existing.getVendorId().equals(vendorId))
                .ifPresent(existing -> {
                    throw new DuplicateResourceException("Vendor username already exists");
                });

        vendor.setVendorName(request.getVendorName());
        vendor.setUsername(request.getUsername());
        vendor.setAddress(request.getAddress());
        vendor.setPhone(request.getPhone());
        vendor.setPassword(passwordEncoder.encode(request.getPassword()));
        Vendor saved = vendorRepository.save(vendor);
        saved.setPassword(null);
        return saved;
    }

    public void deleteVendor(Integer vendorId) {
        Vendor vendor = vendorRepository.findById(vendorId)
                .orElseThrow(() -> new ResourceNotFoundException("Vendor", vendorId));
        vendorRepository.delete(vendor);
    }

    @Transactional(readOnly = true)
    public List<Vendor> getAllVendors() {
        List<Vendor> vendors = vendorRepository.findAll();
        vendors.forEach(v -> v.setPassword(null));
        return vendors;
    }

    @Transactional(readOnly = true)
    public Vendor getVendor(Integer vendorId) {
        Vendor vendor = vendorRepository.findById(vendorId)
                .orElseThrow(() -> new ResourceNotFoundException("Vendor", vendorId));
        vendor.setPassword(null);
        return vendor;
    }

    @Transactional(readOnly = true)
    public Vendor getVendorByName(String vendorName) {
        Vendor vendor = vendorRepository.findByVendorName(vendorName)
                .orElseThrow(() -> new ResourceNotFoundException("Vendor", vendorName));
        vendor.setPassword(null);
        return vendor;
    }

    @Transactional(readOnly = true)
    public List<Feedback> getAllFeedback() {
        return feedbackRepository.findAll();
    }
}
