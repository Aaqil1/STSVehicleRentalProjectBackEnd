package com.vehicle.payrent.vendor.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="vehicle")
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer vehicleId;

    @Column(nullable = false)
    private String vehicleName;

    @Column(nullable = false)
    private boolean isBooked;

    @Column(nullable = false)
    private Integer rentPerday;

    @JsonIgnore
    @OneToMany(mappedBy = "vehicle", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BookingDetail> bookings = new ArrayList<>();

}
