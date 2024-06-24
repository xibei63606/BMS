package com.jzm.bms.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WarnRuleTest {
    @Test
    public void testWarnRule() {
        WarnRule rule = new WarnRule();
        rule.setId(1);
        rule.setWarnId(1);
        rule.setWarnName("电压差报警");
        rule.setBatteryType("铁锂电池");
        rule.setWarningRule("5<=(Ｍx－Mi),报警等级：0\\n3<=(Ｍx－Mi)<5,报警等级：1\\n1<=(Ｍx－Mi)<3,报警等级：2\\n0.6<=(Ｍx－Mi)<1,报警等级：3\\n0.2<=(Ｍx－Mi)<0.6,报警等级：4\\n(Ｍx－Mi)<0.2，不报警");

        assertEquals(1, rule.getId());
        assertEquals(1, rule.getWarnId());
        assertEquals("电压差报警", rule.getWarnName());
        assertEquals("铁锂电池", rule.getBatteryType());
        assertEquals("5<=(Ｍx－Mi),报警等级：0\\n3<=(Ｍx－Mi)<5,报警等级：1\\n1<=(Ｍx－Mi)<3,报警等级：2\\n0.6<=(Ｍx－Mi)<1,报警等级：3\\n0.2<=(Ｍx－Mi)<0.6,报警等级：4\\n(Ｍx－Mi)<0.2，不报警", rule.getWarningRule());
    }
}

