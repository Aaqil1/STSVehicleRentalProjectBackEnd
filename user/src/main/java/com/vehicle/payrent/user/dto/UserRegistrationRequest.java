package com.vehicle.payrent.user.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UserRegistrationRequest {

    @NotBlank
    private String username;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @NotBlank
    private String password;

    @NotBlank
    private String address;

    @NotBlank
    private String gender;

    @NotBlank
    private String phone;
}
