package com.jzm.bms.controller;

import com.jzm.bms.model.CarInfo;
import com.jzm.bms.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/cars")
public class CarController {
    @Autowired
    private CarService carService;

    @GetMapping
    public List<CarInfo> getAllCars() {
        return carService.getAllCarInfos();
    }
    @GetMapping("/{id}")
    public ResponseEntity<CarInfo> getCarInfoByCarId(@PathVariable("id") Integer carId) {
        Optional<CarInfo> carInfo = carService.getCarInfoByCarId(carId);
        return carInfo.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
    @PostMapping
    public CarInfo createCarInfo(@RequestBody CarInfo carInfo){
        return carService.createCarInfo(carInfo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CarInfo> updateCarInfo(@PathVariable("id") Integer carId, @RequestBody CarInfo carInfo) {
        try {
            CarInfo updatedCarInfo = carService.updateCarInfo(carId, carInfo);
            return ResponseEntity.ok(updatedCarInfo);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCarInfo(@PathVariable("id") Integer carId) {
        carService.deleteCarInfo(carId);
        return ResponseEntity.noContent().build();
    }

}
