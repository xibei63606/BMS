package com.jzm.bms.service;

import com.jzm.bms.model.CarInfo;
import com.jzm.bms.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CarService {
    @Autowired
    private CarRepository carRepository;
    @Cacheable(value = "cars")
    public List<CarInfo> getAllCarInfos() {
        return carRepository.findAll();
    }
    @Cacheable(value = "cars", key = "#carId")
    public Optional<CarInfo> getCarInfoByCarId(Integer carId){
        return carRepository.findByCarId(carId);
    }
    @CachePut(value = "caes", key = "#carInfo.carId")
    public CarInfo createCarInfo(CarInfo carInfo) {
        return carRepository.save(carInfo);
    }
    @CachePut(value = "cars", key = "#carId")
    public CarInfo updateCarInfo(Integer carId, CarInfo carInfo) {
        Optional<CarInfo> carInfoOptional = carRepository.findByCarId(carId);
        if (carInfoOptional.isPresent()) {
            CarInfo existingCarInfo = carInfoOptional.get();
            existingCarInfo.setVid(carInfo.getVid());
            existingCarInfo.setBatteryType(carInfo.getBatteryType());
            existingCarInfo.setTotalMileage(carInfo.getTotalMileage());
            existingCarInfo.setBatteryHealth(carInfo.getBatteryHealth());
            return carRepository.save(existingCarInfo);
        } else {
            throw new RuntimeException("Car Info not found for id: " + carId);
        }
    }
    @CacheEvict(value = "cars", key = "#carId")
    public void deleteCarInfo(Integer carId) {
        carRepository.deleteByCarId(carId);
    }

}
