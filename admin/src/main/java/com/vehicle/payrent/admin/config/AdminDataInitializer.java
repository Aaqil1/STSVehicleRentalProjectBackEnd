package com.vehicle.payrent.admin.config;

import com.vehicle.payrent.admin.entity.Admin;
import com.vehicle.payrent.admin.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AdminDataInitializer implements CommandLineRunner {

    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        for (Admin admin : adminRepository.findAll()) {
            String password = admin.getPassword();
            if (password != null && !password.startsWith("$2a$")) {
                admin.setPassword(passwordEncoder.encode(password));
                adminRepository.save(admin);
            }
        }
    }
}
