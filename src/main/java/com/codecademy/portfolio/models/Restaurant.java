package com.codecademy.portfolio.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name="RESTAURANTS")
public class Restaurant {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "restaurant_id_generator"
    )
    @SequenceGenerator(
            name = "restaurant_id_generator",
            sequenceName = "restaurant_id_sequence_name",
            allocationSize = 1,
            initialValue = 10
    )
    private Long id;
    private String name;
    private String city;
    @Column(name="STATE")
    private String state;
    private Integer zipcode;
    @Column(name="PEANUTAVERAGE")
    private Double peanutAverage;
    @Column(name="EGGAVERAGE")
    private Double eggAverage;
    @Column(name="DAIRYAVERAGE")
    private Double dairyAverage;
}
