package com.vehicle.payrent.user.service;

import com.vehicle.payrent.user.dto.BookingRequest;
import com.vehicle.payrent.user.dto.FeedbackRequest;
import com.vehicle.payrent.user.dto.LoginRequest;
import com.vehicle.payrent.user.dto.PaymentRequest;
import com.vehicle.payrent.user.dto.UserRegistrationRequest;
import com.vehicle.payrent.user.entity.BookingDetail;
import com.vehicle.payrent.user.entity.Feedback;
import com.vehicle.payrent.user.entity.User;
import com.vehicle.payrent.user.entity.Vehicle;
import com.vehicle.payrent.user.exception.DuplicateResourceException;
import com.vehicle.payrent.user.exception.InvalidCredentialsException;
import com.vehicle.payrent.user.exception.PaymentVerificationException;
import com.vehicle.payrent.user.exception.ResourceNotFoundException;
import com.vehicle.payrent.user.repository.BookingRepository;
import com.vehicle.payrent.user.repository.FeedbackRepository;
import com.vehicle.payrent.user.repository.UserRepository;
import com.vehicle.payrent.user.repository.VehicleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final VehicleRepository vehicleRepository;
    private final BookingRepository bookingRepository;
    private final FeedbackRepository feedbackRepository;
    private final PasswordEncoder passwordEncoder;

    public User register(UserRegistrationRequest request) {
        userRepository.findByUsername(request.getUsername()).ifPresent(user -> {
            throw new DuplicateResourceException("Username already exists");
        });

        User user = new User();
        user.setUsername(request.getUsername());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setAddress(request.getAddress());
        user.setGender(request.getGender());
        user.setPhone(request.getPhone());
        return userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public void authenticate(LoginRequest request) {
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(InvalidCredentialsException::new);
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException();
        }
    }

    @Transactional(readOnly = true)
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Transactional(readOnly = true)
    public User getUser(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", username));
    }

    @Transactional(readOnly = true)
    public List<Vehicle> findAvailableVehicles() {
        return vehicleRepository.findByIsBookedFalse();
    }

    public BookingDetail bookVehicle(String username, BookingRequest request) {
        User user = getUser(username);
        Vehicle vehicle = vehicleRepository.findById(request.getVehicleId())
                .orElseThrow(() -> new ResourceNotFoundException("Vehicle", request.getVehicleId()));

        if (vehicle.isBooked()) {
            throw new DuplicateResourceException("Vehicle is already booked");
        }

        vehicle.setBooked(true);
        BookingDetail bookingDetail = new BookingDetail();
        bookingDetail.setUser(user);
        bookingDetail.setVehicle(vehicle);
        bookingDetail.setNoOfDays(request.getNoOfDays());
        bookingDetail.setTotalAmount(vehicle.getRentPerday() * request.getNoOfDays());
        vehicleRepository.save(vehicle);
        return bookingRepository.save(bookingDetail);
    }

    public void payRent(PaymentRequest request) {
        BookingDetail bookingDetail = bookingRepository.findById(request.getBookingId())
                .orElseThrow(() -> new ResourceNotFoundException("Booking", request.getBookingId()));
        if (!bookingDetail.getTotalAmount().equals(request.getTotalAmount())) {
            throw new PaymentVerificationException();
        }
    }

    public Feedback submitFeedback(FeedbackRequest request) {
        User user = getUser(request.getUsername());
        Feedback feedback = new Feedback();
        feedback.setFeedback(request.getFeedback() + " - " + user.getUsername());
        return feedbackRepository.save(feedback);
    }
}
