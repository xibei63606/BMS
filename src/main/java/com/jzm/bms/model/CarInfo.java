package com.jzm.bms.model;


import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "car_info_new")
public class CarInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "car_id")
    private Integer carId;
    @Column(name = "vid")
    private String vid;
    @Column(name = "battery_type")
    private String batteryType;
    @Column(name = "total_mileage")
    private Double totalMileage;
    @Column(name = "battery_health")
    private Double batteryHealth;

    // getters and setters
}
