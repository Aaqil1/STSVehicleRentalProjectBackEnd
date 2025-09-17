package com.vehicle.payrent.user.service;

import com.vehicle.payrent.user.dto.BookingRequest;
import com.vehicle.payrent.user.dto.PaymentRequest;
import com.vehicle.payrent.user.dto.UserRegistrationRequest;
import com.vehicle.payrent.user.entity.BookingDetail;
import com.vehicle.payrent.user.entity.User;
import com.vehicle.payrent.user.entity.Vehicle;
import com.vehicle.payrent.user.exception.DuplicateResourceException;
import com.vehicle.payrent.user.repository.BookingRepository;
import com.vehicle.payrent.user.repository.UserRepository;
import com.vehicle.payrent.user.repository.VehicleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ActiveProfiles("test")
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @BeforeEach
    void setUp() {
        bookingRepository.deleteAll();
        vehicleRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void registerEncodesPassword() {
        UserRegistrationRequest request = buildRegistrationRequest("user1");
        User user = userService.register(request);
        assertThat(user.getPassword()).isNotEqualTo(request.getPassword());
    }

    @Test
    void registeringDuplicateUsernameThrowsException() {
        UserRegistrationRequest request = buildRegistrationRequest("user1");
        userService.register(request);
        assertThrows(DuplicateResourceException.class, () -> userService.register(request));
    }

    @Test
    void bookingVehicleMarksVehicleAsBooked() {
        UserRegistrationRequest request = buildRegistrationRequest("user2");
        userService.register(request);

        Vehicle vehicle = new Vehicle();
        vehicle.setVehicleName("Hatchback");
        vehicle.setBooked(false);
        vehicle.setRentPerday(500);
        vehicleRepository.save(vehicle);

        BookingRequest bookingRequest = new BookingRequest();
        bookingRequest.setVehicleId(vehicle.getVehicleId());
        bookingRequest.setNoOfDays(2);

        BookingDetail bookingDetail = userService.bookVehicle("user2", bookingRequest);

        assertThat(bookingDetail.getTotalAmount()).isEqualTo(1000);
        assertThat(vehicleRepository.findById(vehicle.getVehicleId()).get().isBooked()).isTrue();

        PaymentRequest paymentRequest = new PaymentRequest();
        paymentRequest.setBookingId(bookingDetail.getBookingId());
        paymentRequest.setTotalAmount(1000);
        userService.payRent(paymentRequest);
    }

    private UserRegistrationRequest buildRegistrationRequest(String username) {
        UserRegistrationRequest request = new UserRegistrationRequest();
        request.setUsername(username);
        request.setFirstName("First");
        request.setLastName("Last");
        request.setPassword("password");
        request.setAddress("Address");
        request.setGender("M");
        request.setPhone("9999");
        return request;
    }
}
