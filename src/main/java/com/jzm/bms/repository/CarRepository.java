package com.jzm.bms.repository;

import com.jzm.bms.model.CarInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CarRepository extends JpaRepository<CarInfo, Integer> {

    Optional<CarInfo> findByCarId(Integer carId);

    void deleteByCarId(Integer carId);
}
