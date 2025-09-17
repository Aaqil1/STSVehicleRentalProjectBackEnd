package com.vehicle.payrent.vendor.controller;

import com.vehicle.payrent.vendor.dto.ApiResponse;
import com.vehicle.payrent.vendor.dto.LoginRequest;
import com.vehicle.payrent.vendor.dto.VehicleRequest;
import com.vehicle.payrent.vendor.entity.BookingDetail;
import com.vehicle.payrent.vendor.entity.Vehicle;
import com.vehicle.payrent.vendor.service.VendorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/vendor")
@Validated
@RequiredArgsConstructor
public class VendorController {

    private final VendorService vendorService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> vendorLogin(@Valid @RequestBody LoginRequest request) {
        vendorService.authenticate(request);
        return ResponseEntity.ok(ApiResponse.builder().success(true).message("Login successful").build());
    }

    @PutMapping("/vehicles/{vehicleId}")
    public ResponseEntity<Vehicle> updateVehicle(@PathVariable Integer vehicleId,
                                                 @Valid @RequestBody VehicleRequest request) {
        Vehicle vehicle = vendorService.updateVehicle(vehicleId, request);
        return ResponseEntity.ok(vehicle);
    }

    @DeleteMapping("/vehicles/{vehicleId}")
    public ResponseEntity<ApiResponse> deleteVehicle(@PathVariable Integer vehicleId) {
        vendorService.deleteVehicle(vehicleId);
        return ResponseEntity.ok(ApiResponse.builder().success(true).message("Vehicle deleted").build());
    }

    @GetMapping("/bookings")
    public ResponseEntity<List<BookingDetail>> getAllBookingDetails() {
        return ResponseEntity.ok(vendorService.getAllBookingDetails());
    }

    @GetMapping("/vehicles")
    public ResponseEntity<List<Vehicle>> getAllVehicleDetails() {
        return ResponseEntity.ok(vendorService.getAllVehicleDetails());
    }

    @GetMapping("/vehicles/{vehicleId}")
    public ResponseEntity<Vehicle> getVehicle(@PathVariable Integer vehicleId) {
        return ResponseEntity.ok(vendorService.getVehicle(vehicleId));
    }
}
