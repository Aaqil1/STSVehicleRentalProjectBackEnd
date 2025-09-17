package com.vehicle.payrent.admin.security;

import com.vehicle.payrent.admin.entity.Admin;
import com.vehicle.payrent.admin.exception.InvalidCredentialsException;
import com.vehicle.payrent.admin.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminUserDetailsService implements UserDetailsService {

    private final AdminRepository adminRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Admin admin = adminRepository.findByAdminId(username)
                .orElseThrow(InvalidCredentialsException::new);
        return new AdminUserDetails(admin);
    }
}
