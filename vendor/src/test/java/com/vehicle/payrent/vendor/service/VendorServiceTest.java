package com.vehicle.payrent.vendor.service;

import com.vehicle.payrent.vendor.dto.LoginRequest;
import com.vehicle.payrent.vendor.dto.VehicleRequest;
import com.vehicle.payrent.vendor.entity.Vehicle;
import com.vehicle.payrent.vendor.entity.Vendor;
import com.vehicle.payrent.vendor.repository.VehicleRepository;
import com.vehicle.payrent.vendor.repository.VendorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
class VendorServiceTest {

    @Autowired
    private VendorService vendorService;

    @Autowired
    private VendorRepository vendorRepository;

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private Vendor vendor;

    @BeforeEach
    void setUp() {
        vendorRepository.deleteAll();
        vehicleRepository.deleteAll();
        vendor = new Vendor();
        vendor.setVendorName("Vendor");
        vendor.setUsername("vendor");
        vendor.setPassword(passwordEncoder.encode("password"));
        vendor.setPhone("123");
        vendor.setAddress("Address");
        vendorRepository.save(vendor);
    }

    @Test
    void authenticateAcceptsValidCredentials() {
        LoginRequest request = new LoginRequest();
        request.setUsername("vendor");
        request.setPassword("password");
        vendorService.authenticate(request);
    }

    @Test
    void updateVehiclePersistsChanges() {
        Vehicle vehicle = new Vehicle();
        vehicle.setVehicleName("SUV");
        vehicle.setBooked(false);
        vehicle.setRentPerday(1000);
        vehicleRepository.save(vehicle);

        VehicleRequest request = new VehicleRequest();
        request.setVehicleName("SUV Updated");
        request.setBooked(true);
        request.setRentPerDay(1200);

        Vehicle updated = vendorService.updateVehicle(vehicle.getVehicleId(), request);

        assertThat(updated.getVehicleName()).isEqualTo("SUV Updated");
        assertThat(updated.isBooked()).isTrue();
    }
}
