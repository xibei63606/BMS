package com.jzm.bms;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;
import com.jzm.bms.model.CarInfo;
import com.jzm.bms.model.WarnRule;
import com.jzm.bms.repository.CarRepository;
import com.jzm.bms.repository.WarnRuleRepository;
import com.jzm.bms.service.WarnService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

@SpringBootTest
public class WarnServiceTest {
    @Mock
    private WarnRuleRepository warnRuleRepository;


    @Mock
    private CarRepository carRepository;

    @InjectMocks
    private WarnService warnService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    public void testProcessWarn_WithValidData_ShouldReturnExpectedResult() {
        List<Map<String, Object>> warnData = new ArrayList<>();
        Map<String, Object> warn = new HashMap<>();
        warn.put("carId", 2);
        warn.put("warnId", 2);
        warn.put("signal",  "{\"Ix\":12.0,\"Ii\":11.7}");
        warnData.add(warn);

        CarInfo carInfo = new CarInfo();
        carInfo.setCarId(2);
        carInfo.setBatteryType("铁锂电池");

        WarnRule warnRule = new WarnRule();
        warnRule.setWarnId(2);
        warnRule.setWarnName("电流差报警");
        warnRule.setBatteryType("铁锂电池");
        warnRule.setWarningRule("1<=(Ix－Ii),报警等级：0\n0.5<=(Ix－Ii)<1,报警等级：1\n0.2<=(Ix－Ii)<0.5,报警等级：2\n(Ix－Ii)<0.2，不报警");

        when(carRepository.findByCarId(2)).thenReturn(Optional.of(carInfo));
        when(warnRuleRepository.findAllByBatteryType("铁锂电池")).thenReturn(Collections.singletonList(warnRule));
        when(warnRuleRepository.findAllByWarnIdAndBatteryType(2, "铁锂电池")).thenReturn(Collections.singletonList(warnRule));

        Map<String, Object> result = warnService.processWarn(warnData);

        Assertions.assertEquals(200, result.get("status"));
        Assertions.assertEquals("ok", result.get("msg"));
        List<Map<String, Object>> data = (List<Map<String, Object>>) result.get("data");
        Assertions.assertFalse(data.isEmpty());
        Assertions.assertEquals(1, data.size());
        Assertions.assertEquals("电流差报警", data.get(0).get("告警名称"));
        Assertions.assertEquals("2", data.get(0).get("告警等级"));
    }

}
