package com.vehicle.payrent.admin.repository;

import com.vehicle.payrent.admin.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Integer> {

    List<Vehicle> findByIsBookedFalse();
}
