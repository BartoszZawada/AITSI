package com.AITSI.vehicle;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Data
public class Vehicle {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String imageLink;
    private String vehicleType;
    private String brand;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate productionYear;
    private String colour;
    private Integer engineCapacity;
    private Boolean isAvailable = true;
    private Long price;

    @OneToMany(cascade = ALL,orphanRemoval = true)
    @JoinColumn(name = "orderId")
    private List<VehicleOrder> orders = new ArrayList<>();
}
