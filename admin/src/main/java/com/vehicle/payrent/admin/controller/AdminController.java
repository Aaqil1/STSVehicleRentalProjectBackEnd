package com.vehicle.payrent.admin.controller;

import com.vehicle.payrent.admin.dto.ApiResponse;
import com.vehicle.payrent.admin.dto.LoginRequest;
import com.vehicle.payrent.admin.dto.VehicleRequest;
import com.vehicle.payrent.admin.dto.VendorRequest;
import com.vehicle.payrent.admin.entity.*;
import com.vehicle.payrent.admin.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/admin")
@Validated
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> adminLogin(@Valid @RequestBody LoginRequest request) {
        adminService.authenticate(request);
        return ResponseEntity.ok(ApiResponse.builder().success(true).message("Login successful").build());
    }

    @PostMapping("/vehicles")
    public ResponseEntity<Vehicle> addVehicle(@Valid @RequestBody VehicleRequest request) {
        Vehicle vehicle = adminService.createVehicle(request);
        return new ResponseEntity<>(vehicle, HttpStatus.CREATED);
    }

    @PutMapping("/vehicles/{vehicleId}")
    public ResponseEntity<Vehicle> updateVehicle(@PathVariable Integer vehicleId,
                                                 @Valid @RequestBody VehicleRequest request) {
        Vehicle vehicle = adminService.updateVehicle(vehicleId, request);
        return ResponseEntity.ok(vehicle);
    }

    @DeleteMapping("/vehicles/{vehicleId}")
    public ResponseEntity<ApiResponse> deleteVehicle(@PathVariable Integer vehicleId) {
        adminService.deleteVehicle(vehicleId);
        return ResponseEntity.ok(ApiResponse.builder().success(true).message("Vehicle deleted").build());
    }

    @GetMapping("/vehicles")
    public ResponseEntity<List<Vehicle>> getAllVehicle() {
        return ResponseEntity.ok(adminService.getAllVehicles());
    }

    @GetMapping("/bookings")
    public ResponseEntity<List<BookingDetail>> getAllBookingDetails() {
        return ResponseEntity.ok(adminService.getAllBookingDetails());
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUser() {
        return ResponseEntity.ok(adminService.getAllUsers());
    }

    @PostMapping("/vendors")
    public ResponseEntity<Vendor> addVendor(@Valid @RequestBody VendorRequest request) {
        Vendor vendor = adminService.createVendor(request);
        return new ResponseEntity<>(vendor, HttpStatus.CREATED);
    }

    @PutMapping("/vendors/{vendorId}")
    public ResponseEntity<Vendor> updateVendor(@PathVariable Integer vendorId,
                                               @Valid @RequestBody VendorRequest request) {
        Vendor vendor = adminService.updateVendor(vendorId, request);
        return ResponseEntity.ok(vendor);
    }

    @DeleteMapping("/vendors/{vendorId}")
    public ResponseEntity<ApiResponse> deleteVendor(@PathVariable Integer vendorId) {
        adminService.deleteVendor(vendorId);
        return ResponseEntity.ok(ApiResponse.builder().success(true).message("Vendor deleted").build());
    }

    @GetMapping("/vendors")
    public ResponseEntity<List<Vendor>> getAllvendor() {
        return ResponseEntity.ok(adminService.getAllVendors());
    }

    @GetMapping("/vendors/{vendorId}")
    public ResponseEntity<Vendor> getVendor(@PathVariable Integer vendorId) {
        return ResponseEntity.ok(adminService.getVendor(vendorId));
    }

    @GetMapping("/vendors/by-name/{vendorName}")
    public ResponseEntity<Vendor> getVendorByName(@PathVariable String vendorName) {
        return ResponseEntity.ok(adminService.getVendorByName(vendorName));
    }

    @GetMapping("/feedback")
    public ResponseEntity<List<Feedback>> getAllFeedback() {
        return ResponseEntity.ok(adminService.getAllFeedback());
    }
}
