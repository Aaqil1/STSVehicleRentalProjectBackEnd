package com.vehicle.payrent.vendor.security;

import com.vehicle.payrent.vendor.entity.Vendor;
import com.vehicle.payrent.vendor.exception.InvalidCredentialsException;
import com.vehicle.payrent.vendor.repository.VendorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VendorAccountDetailsService implements UserDetailsService {

    private final VendorRepository vendorRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Vendor vendor = vendorRepository.findByUsername(username)
                .orElseThrow(InvalidCredentialsException::new);
        return new VendorAccountDetails(vendor);
    }
}
