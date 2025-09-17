package com.vehicle.payrent.admin.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Vendor {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer vendorId;

    private String vendorName;

    private String username;

    private String password;

    private String phone;

    private String address;

    @Column(nullable = false)
    private String role = "ROLE_VENDOR";
}
