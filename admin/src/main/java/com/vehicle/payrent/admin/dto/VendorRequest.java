package com.vehicle.payrent.admin.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class VendorRequest {

    @NotBlank
    private String vendorName;

    @NotBlank
    private String username;

    @NotBlank
    private String password;

    @NotBlank
    private String address;

    @NotBlank
    private String phone;
}
