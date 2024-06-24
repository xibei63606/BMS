package com.jzm.bms.service;

import com.jzm.bms.model.CarInfo;
import com.jzm.bms.model.WarnRule;
import com.jzm.bms.repository.CarRepository;
import com.jzm.bms.repository.WarnRuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service     // 标注为Spring的Service
public class WarnService {
    @Autowired
    private WarnRuleRepository warnRuleRepository;
    @Autowired
    private CarRepository carRepository;

    @Cacheable(value = "warnRules", key = "#WarnData")
    public Map<String, Object> processWarn(List<Map<String, Object>> WarnData) {
        // 处理告警数据
        Map<String, Object> result = new HashMap<>();
        List<Map<String, Object>> data = new ArrayList<>();
        try {
            for (Map<String, Object> warn : WarnData) {
                Integer carId = (Integer) warn.get("carId");
                Integer warnId = warn.containsKey("warnId") ? (Integer) warn.get("warnId") : null;
                String signal = (String) warn.get("signal");

                Optional<CarInfo> carInfoOpt = carRepository.findByCarId(carId);
                if (!carInfoOpt.isPresent()) {
                    throw new IllegalArgumentException("车辆不存在");
                }
                CarInfo carInfo = carInfoOpt.get();
                List<WarnRule> warnRules;

                if (warnId != null) {
                    warnRules = warnRuleRepository.findAllByWarnIdAndBatteryType(warnId, carInfo.getBatteryType());
                } else {
                    warnRules = warnRuleRepository.findAllByBatteryType(carInfo.getBatteryType());
                }
                if (warnRules.isEmpty()) {
                    throw new RuntimeException("没有找到对应的告警规则:" + carId + "with batteryType:" + carInfo.getBatteryType());
                }

                for (WarnRule rule : warnRules) {
                    String warnlevel = evaluate(rule.getWarningRule(), signal);
                    if (!warnlevel.equals("-1")) {
                        Map<String, Object> warnResult = new HashMap<>();
                        warnResult.put("告警等级", warnlevel);
                        warnResult.put("告警名称", rule.getWarnName());
                        warnResult.put("电池类型", carInfo.getBatteryType());
                        warnResult.put("车架编号", carId);
                        data.add(warnResult);
                    }
                }

            }
            result.put("data", data);
            result.put("msg", "ok");
            result.put("status", 200);
        } catch (Exception e) {
            result.put("data", Collections.emptyList());
            result.put("msg", e.getMessage());
            result.put("status", 500);
        }
        return result;
    }

// 根据电压电流差，判断告警等级
    public String evaluate(String warningRule, String signal){
        Map<String, Double> signalMap = parseSignal(signal);
        String[] rules = warningRule.split("\n");

        for (String rule : rules) {
            //解析判断条件与报警等级
            String[] parts = rule.split(",报警等级：");
            String condition = parts[0].trim();
            String level = parts.length == 2 ? parts[1].trim() : "不报警";

            //解析判断条件对应判断阈值
            String[] rangeParts = condition.split("<=");
            double diff = 0.0;
            if (rangeParts.length == 2) {
                double lowerBound = Double.parseDouble(rangeParts[0].trim());
                if (condition.contains("Mx") && condition.contains("Mi")) {
                    double Mx = signalMap.get("Mx");
                    double Mi = signalMap.get("Mi");
                    diff = signalMap.get("Mx") - signalMap.get("Mi");
                }else if (condition.contains("Ix") && condition.contains("Ii")) {
                    diff = signalMap.get("Ix") - signalMap.get("Ii");
                }
                if (lowerBound <= diff) {
                    return level;
                }
            }else if (rangeParts.length == 1){
                return level;
            }

        }
        return "-1";
    }

//解析signal中的电压电流信号
    private Map<String, Double> parseSignal(String signal) {
        Map<String, Double> signalMap = new HashMap<>();
        signal = signal.replaceAll("[{}\"]", "");
        String[] keyValuePairs = signal.split(",");
        for (String keyValuePair : keyValuePairs) {
            String[] keyValue = keyValuePair.split(":");
            signalMap.put(keyValue[0], Double.parseDouble(keyValue[1]));
        }
        return signalMap;
    }
}


