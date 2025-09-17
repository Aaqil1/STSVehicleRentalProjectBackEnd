package com.vehicle.payrent.admin.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer userId;

    @Column(unique = true,nullable = false)
    @NotBlank(message = "username should not be blank")
    private String username;

    @Column(nullable = false)
    @NotBlank(message = "firstname should not be blank")
    private String firstName;

    @Column(nullable = false)
    @NotBlank(message = "lastname should not be blank")
    private String lastName;

    @Column(nullable = false)
    @NotBlank(message = "password should not be blank")
    private String password;

    @Column(nullable = false)
    @NotBlank(message = "address should not be blank")
    private String address;

    @Column(nullable = false)
    @NotBlank(message = "gender should not be blank")
    private String gender;

    @Column(nullable = false)
    private String Phone;

    @Column(nullable = false)
    private String role = "ROLE_USER";

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BookingDetail> bookings = new ArrayList<>();

}
