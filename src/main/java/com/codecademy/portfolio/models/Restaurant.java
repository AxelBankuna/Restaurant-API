package com.codecademy.portfolio.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name="RESTAURANT")
public class Restaurant {
    @Id
    @GeneratedValue
    private Long id;
    @Column
    private String name;
    @Column()
    private Integer zipcode;
    @Column(name="PEANUT")
    private Long peanutAverage;
    @Column(name="EGG")
    private Long eggAverage;
    @Column(name="DAIRY")
    private Long dairyAverage;
}
