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
    private String city;
    @Column(name="STATE")
    private String state;
    private Integer zipcode;
    @Column(name="PEANUTINTEREST")
    private Boolean peanutInterest;
    @Column(name="EGGINTEREST")
    private Boolean eggInterest;
    @Column(name="DAIRYINTEREST")
    private Boolean dairyInterest;
}
