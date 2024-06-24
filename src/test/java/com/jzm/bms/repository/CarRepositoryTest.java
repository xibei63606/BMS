package com.jzm.bms.repository;

import com.jzm.bms.model.CarInfo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // 禁用自动替换数据源
public class CarRepositoryTest {

    @Autowired
    private CarRepository carRepository;

    @Test
    public void testSaveCarInfo() {
        // 创建一个 CarInfo 对象
        CarInfo carInfo = new CarInfo();
        carInfo.setCarId(5);
        carInfo.setVid("20240606");
        carInfo.setBatteryType("铁锂电池");
        carInfo.setTotalMileage(1000.0);
        carInfo.setBatteryHealth(95.0);

        // 保存到数据库
        CarInfo savedCarInfo = carRepository.save(carInfo);

        // 断言保存成功
        assertNotNull(savedCarInfo.getCarId());
    }

    @Test
    public void testFindById() {
        // 保存一个 CarInfo 对象到数据库
        CarInfo carInfo = new CarInfo();
        carInfo.setCarId(6);
        carInfo.setVid("20240501");
        carInfo.setBatteryType("三元电池");
        carInfo.setTotalMileage(500.0);
        carInfo.setBatteryHealth(90.0);
        carRepository.save(carInfo);

        // 查询并断言能够找到该 CarInfo 对象
        CarInfo foundCarInfo = carRepository.findById(carInfo.getCarId()).orElse(null);
        assertNotNull(foundCarInfo);
        assertEquals("20240501", foundCarInfo.getVid());
    }

    // 可以编写其他测试方法来覆盖更多的 Repository 方法，如 findAll、deleteById 等
}
