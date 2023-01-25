package com.codecademy.portfolio.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Objects;

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
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String city;
    @Column(name="STATE")
    private String state;
    @Column(nullable = false)
    private Integer zipcode;
    @Column(name="PEANUTAVERAGE")
    private Double peanutAverage;
    @Column(name="EGGAVERAGE")
    private Double eggAverage;
    @Column(name="DAIRYAVERAGE")
    private Double dairyAverage;
    @Column(name="AVERAGESCORE")
    private Double averageScore;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Restaurant that)) return false;
        return name.equals(that.name) && city.equals(that.city) && state.equals(that.state) && zipcode.equals(that.zipcode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, city, state, zipcode);
    }
}
