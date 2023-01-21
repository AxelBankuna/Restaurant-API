package com.codecademy.portfolio.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name="USERS")
public class User {
    @Id
    @GeneratedValue
    private Long id;
    @Column(unique = true, updatable = false)
    private String username;
    private String City;
    private String state;
    private Integer zipcode;
    private Boolean peanutInterest;
    private Boolean eggInterest;
    private Boolean dairyInterest;
}
