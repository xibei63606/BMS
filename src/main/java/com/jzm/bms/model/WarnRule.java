package com.jzm.bms.model;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "warn_rule_new")
public class WarnRule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "warn_id")
    private Integer warnId;
    @Column(name = "warn_name")
    private String warnName;
    @Column(name = "battery_type")
    private String batteryType;
    @Column(name = "warning_rule")
    private String warningRule;

    // getters and setters

}
