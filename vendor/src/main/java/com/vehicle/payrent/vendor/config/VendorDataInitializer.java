package com.vehicle.payrent.vendor.config;

import com.vehicle.payrent.vendor.entity.Vendor;
import com.vehicle.payrent.vendor.repository.VendorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class VendorDataInitializer implements CommandLineRunner {

    private final VendorRepository vendorRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        for (Vendor vendor : vendorRepository.findAll()) {
            String password = vendor.getPassword();
            if (password != null && !password.startsWith("$2a$")) {
                vendor.setPassword(passwordEncoder.encode(password));
                vendorRepository.save(vendor);
            }
        }
    }
}
