package com.vehicle.payrent.admin.service;

import com.vehicle.payrent.admin.dto.LoginRequest;
import com.vehicle.payrent.admin.dto.VehicleRequest;
import com.vehicle.payrent.admin.dto.VendorRequest;
import com.vehicle.payrent.admin.entity.Admin;
import com.vehicle.payrent.admin.entity.Vehicle;
import com.vehicle.payrent.admin.exception.DuplicateResourceException;
import com.vehicle.payrent.admin.repository.AdminRepository;
import com.vehicle.payrent.admin.repository.VehicleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ActiveProfiles("test")
class AdminServiceTest {

    @Autowired
    private AdminService adminService;

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        vehicleRepository.deleteAll();
        adminRepository.deleteAll();
        Admin admin = new Admin();
        admin.setAdminId("admin");
        admin.setPassword(passwordEncoder.encode("password"));
        adminRepository.save(admin);
    }

    @Test
    void createVehiclePersistsData() {
        VehicleRequest request = new VehicleRequest();
        request.setVehicleName("Sedan");
        request.setBooked(false);
        request.setRentPerDay(1000);

        Vehicle vehicle = adminService.createVehicle(request);

        assertThat(vehicle.getVehicleId()).isNotNull();
        assertThat(vehicleRepository.findById(vehicle.getVehicleId())).isPresent();
    }

    @Test
    void creatingVendorWithDuplicateUsernameThrowsException() {
        VendorRequest request = new VendorRequest();
        request.setVendorName("Vendor A");
        request.setUsername("vendor");
        request.setPassword("secret");
        request.setAddress("Address");
        request.setPhone("123");

        adminService.createVendor(request);

        assertThrows(DuplicateResourceException.class, () -> adminService.createVendor(request));
    }

    @Test
    void authenticateMatchesEncodedPassword() {
        LoginRequest request = new LoginRequest();
        request.setUsername("admin");
        request.setPassword("password");

        adminService.authenticate(request);
    }
}
