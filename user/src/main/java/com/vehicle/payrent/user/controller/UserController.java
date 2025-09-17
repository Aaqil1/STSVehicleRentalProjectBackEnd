package com.vehicle.payrent.user.controller;

import com.vehicle.payrent.user.dto.*;
import com.vehicle.payrent.user.entity.BookingDetail;
import com.vehicle.payrent.user.entity.Feedback;
import com.vehicle.payrent.user.entity.User;
import com.vehicle.payrent.user.entity.Vehicle;
import com.vehicle.payrent.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@Slf4j
@RestController
@RequestMapping("/api/users")
@Validated
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<User> createNewUser(@Valid @RequestBody UserRegistrationRequest request) {
        log.info("Registering user {}", request.getUsername());
        User user = userService.register(request);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUser() {
        return ResponseEntity.ok(userService.findAll());
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> userlogin(@Valid @RequestBody LoginRequest request) {
        userService.authenticate(request);
        return ResponseEntity.ok(ApiResponse.builder().success(true).message("Login successful").build());
    }

    @GetMapping("/{username}/vehicles")
    public ResponseEntity<List<Vehicle>> findAllVehicles(@PathVariable String username) {
        User user = userService.getUser(username);
        log.debug("Listing vehicles for user {}", user.getUsername());
        return ResponseEntity.ok(userService.findAvailableVehicles());
    }

    @PostMapping("/{username}/bookings")
    public ResponseEntity<BookingDetail> bookVehicle(@PathVariable String username,
                                                     @Valid @RequestBody BookingRequest request) {
        BookingDetail bookingDetail = userService.bookVehicle(username, request);
        return ResponseEntity.ok(bookingDetail);
    }

    @PostMapping("/payments")
    public ResponseEntity<ApiResponse> payRent(@Valid @RequestBody PaymentRequest request) {
        userService.payRent(request);
        return ResponseEntity.ok(ApiResponse.builder().success(true).message("Payment verified").build());
    }

    @PostMapping("/feedback")
    public ResponseEntity<Feedback> feedback(@Valid @RequestBody FeedbackRequest request) {
        Feedback feedback = userService.submitFeedback(request);
        return new ResponseEntity<>(feedback, HttpStatus.CREATED);
    }
}
