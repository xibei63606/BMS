package com.jzm.bms.repository;

import com.jzm.bms.model.WarnRule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WarnRuleRepository extends JpaRepository<WarnRule, Integer> {
    List<WarnRule> findAllByWarnIdAndBatteryType(Integer warnId, String batteryType);
    List<WarnRule> findAllByBatteryType(String batteryType);
}
